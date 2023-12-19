import React, { FC, useEffect, useState } from 'react';
import { Project } from "../../models/project";
import '../../static/annotations.css'
import './xml.css'

interface XMLProps {
  project: Project;
  onTextSelection: (text: string, offset: number) => void;
}

const LoadXML: FC<XMLProps> = ({ project, onTextSelection }) => {
  const [renderXML, setRenderXML] = useState(false);
  const [annotationStyles, setAnnotationStyles] = useState({});

  useEffect(() => {
    // Convert the XML string to a readable DOM object
    let parser = new DOMParser();
    let xml = parser.parseFromString(project.xml_content, "application/xml");
    setRenderXML(true);
    fetchAnnotationsAndStyles(xml);
  }, [project.xml_content]);

  /**
   * Fetches annotation data for each annotation tag in the given XML document.
   * For each annotation, it retrieves the color associated with its law class and adds it as a CSS rule, which is later
   * applied to the page.
   *
   * @param {Document} xmlDoc - The XML document containing annotation tags.
   */
  const fetchAnnotationsAndStyles = async (xmlDoc: Document) => {
    const annotations = xmlDoc.getElementsByTagName('annotation');
    let newAnnotationStyles: any = {};

    // Loop through all annotations and fetch the annotation data
    // @ts-ignore
    for (let annotation of annotations) {
      const id = annotation.getAttribute('id');
      if (id) {
        const response = await fetch(`http://localhost:8000/api/annotations/${id}`);
        if (response.ok) {
          const annotationData = await response.json();
          newAnnotationStyles[`${id}`] = annotationData?.lawClass.color;
        } else {
          console.error('Failed to fetch annotation data');
        }
      }
    }

    setAnnotationStyles(newAnnotationStyles);
  };


  /**
   * Generates CSS styles for annotations based on their id and the associated color.
   * It creates a string of CSS rules that is later added to the HTML.
   *
   * @returns {string} A string of CSS rules for styling annotations.
   */
  const renderStyles = () => {
    let styleString = "";
    for (const [selector, color] of Object.entries(annotationStyles)) {
      styleString += `annotation[id="${selector}"] { background-color: ${color}; border-radius: 4px; outline: ${color} solid 1px }\n`;
    }
    return styleString;
  };


  const handleShow = () => {
    const selection = window.getSelection();
    if (selection && selection.rangeCount > 0) {
      const range = selection.getRangeAt(0);
      const text = range.toString();
      const startOffset = calculateOffset(range.startContainer, range.startOffset);

      if (text) {
        onTextSelection(text, startOffset);
      }
    }
  };

  function calculateOffset(node: Node, offset: number): number {
    let count = offset;
    while (node.previousSibling) {
      node = node.previousSibling;
      count += node.textContent?.length || 0;
    }
    return count;
  }


  return (
      <>
      <style>
        {renderStyles()}
      </style>
      <>
        <p className="xml-content" onMouseUp={handleShow} dangerouslySetInnerHTML={{__html: renderXML && project.xml_content}}/>
      </>
      </>
  );
}

export default LoadXML;
