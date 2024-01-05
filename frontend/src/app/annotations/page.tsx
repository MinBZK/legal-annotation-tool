"use client";
import AnnotationView from '../annotation-view/annotation-view';
import '../static/annotations.css';
import {getProjectById} from '../services/project';
import { Project } from '../models/project';
import { useSearchParams } from 'next/navigation'
import {useEffect, useState} from "react";
import LoadXML from "./comment/render-xml";
import CreateAnnotation from "./create-annotation/create-annotation";

const AnnotationPage = () => {

    const [projectData, setProjectData] = useState<Project | null>(null);
    const [isTextSelected, setIsTextSelected] = useState(false);
    const [selectedText, setSelectedText] = useState("");
    const [startOffset, setStartOffset] = useState(0);
    const [reloadXML, setReloadXML] = useState(false);

    // Get id from url
    const searchParams = useSearchParams();
    let id: number = 0;

    if (searchParams != null) {
        const param = searchParams.get('id');
        id = param != null ? parseInt(param) : 0;
        if (isNaN(id)) id = 0;
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getProjectById(id) as Project;
                setProjectData(data);
            } catch (error) {
                // Handle error
                console.error('Error fetching project data:', error);
            }
        };

        fetchData();
    }, [id, reloadXML]);

    const handleTextSelection = (text: string, offset: number) => {
        setIsTextSelected(true);
        setSelectedText(text);
        setStartOffset(offset)
    };

    const handleCloseCreate = () => {
        setIsTextSelected(false);
        setSelectedText("");
        setStartOffset(0);
    }

    const handleAnnotationSaved = () => {
        setReloadXML((prev) => !prev);
    };

    /**
     * Delete the <annotation> tags with the given id from the XML and update the XML in the database.
     *
     * @param annotationId The id of the annotation to delete
     */
    const handleAnnotationDeleted = async (annotationId: number) => {
        // Get the XML content
        let xml = projectData?.xml_content

        if (xml == null || projectData == null) return;

        // Convert the XML string to a DOM object
        let parser = new DOMParser();
        let xmlDoc = parser.parseFromString(xml, "text/xml");

        // Get the annotation with the given id
        let annotation = xmlDoc.getElementById(annotationId.toString());

        if (annotation == null) return;

        // Remove the annotation tags by replacing them with the innerHTML
        annotation.replaceWith(annotation.innerHTML);

        // Convert the DOM object back to a string
        xml = new XMLSerializer().serializeToString(xmlDoc);
        projectData.xml_content = xml;

        // Reload the XML
        setReloadXML((prev) => !prev);

        // Update the XML in the database
        await updateXML(annotationId);
    }

    /**
     * Update the XML in the database after removing an annotation
     *
     * @param annotationId The id of the annotation to remove
     */
    const updateXML = async (annotationId: number) => {
        if (projectData == null) return;

        try {
            // Remove the annotation from the projectData to prevent it from being saved again
            projectData.annotations = projectData.annotations.filter((annotation) => annotation.id !== annotationId);

            // Create a copy of Project to avoid mutating the original object
            const updatedProject = {
                ...projectData,
            };

            const response = await fetch('http://localhost:8000/api/saveXml', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedProject)
            });

            if (!response.ok) {
                throw new Error('Failed to update XML');
            }

            console.log('XML updated successfully');
        } catch (error) {
            console.error('Error updating XML:', error);
        }
    }


    return (
    <>
      <nav className="navbar">
        {<div className="navbar-title">Legal Annotation Tool</div>}
      </nav>
      <main className='d-flex'>
        <section className="left-column">
            {projectData && <LoadXML project={projectData}  onTextSelection={handleTextSelection}
            />}
        </section>
        <section className="right-column">
            {isTextSelected ? (
                // Render Create annotation when text is selected
                <CreateAnnotation selectedText={selectedText} startOffset={startOffset} onClose={handleCloseCreate} onAnnotationSaved={handleAnnotationSaved} // Pass the callback
                />
            ) : (
                // Render AnnotationView when text is not selected
                <AnnotationView onAnnotationDelete={handleAnnotationDeleted}/>
            )}
        </section>
      </main>
    </>
  );
}

export default AnnotationPage;
