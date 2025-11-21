package com.Matthas.exportTools.KMZConverterToDXF.KMLParsers;

import com.Matthas.exportTools.KMZConverterToDXF.KMZObjects.KMLObject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class KMLParser {

    public static List<KMLObject> parse(String xmlContent) {
        List<KMLObject> objects = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
            doc.getDocumentElement().normalize();

            NodeList placemarks = doc.getElementsByTagName("Placemark");
            for (int i = 0; i < placemarks.getLength(); i++) {
                Element placemark = (Element) placemarks.item(i);

                // Use id attribute
                String id = placemark.hasAttribute("id") ? placemark.getAttribute("id") : "Placemark_" + i;
                KMLObject obj = new KMLObject(id);

                // Parse coordinates
                Node coordsNode = placemark.getElementsByTagName("coordinates").item(0);
                if (coordsNode != null) {
                    String coordsText = coordsNode.getTextContent().trim();
                    String[] pairs = coordsText.split("\\s+"); // Split by space
                    for (String pair : pairs) {
                        String[] parts = pair.split(",");
                        if (parts.length >= 2) {
                            double lon = Double.parseDouble(parts[0]);
                            double lat = Double.parseDouble(parts[1]);
                            obj.addCoordinate(lon, lat);
                        }
                    }
                }
                // Collect attributes
                NodeList children = placemark.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        obj.addAttribute(child.getNodeName(), child.getTextContent().trim());
                    }
                }

                objects.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }
}