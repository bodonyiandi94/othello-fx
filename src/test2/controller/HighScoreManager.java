/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import test2.model.HighScoreEntry;

public class HighScoreManager {

    private static String XML_PATH = "hi-score.xml";

    private static HighScoreManager instance = new HighScoreManager();

    public static HighScoreManager getInstance() {
        return instance;
    }

    private Document loadXML() {
        Document result = null;
        try {
            File fXmlFile = new File(XML_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            result = dBuilder.parse(fXmlFile);
        } catch (Exception ex) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private Document createDocument() {
        Document result = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);
            saveXML(doc);

        } catch (Exception e) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    private void saveXML(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult stremResult = new StreamResult(new File(XML_PATH));

            transformer.transform(source, stremResult);

        } catch (Exception e) {
            Logger.getLogger(HighScoreManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertOrUpdateEntry(HighScoreEntry hsEntry) {
        Document doc = loadXML();
        if (doc == null) {
            doc = createDocument();
        }

        Element element = doc.getDocumentElement();
        NodeList entries = element.getElementsByTagName("entry");
        boolean found = false;

        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);

            if (entry.getElementsByTagName("name").item(0).getTextContent().equals(hsEntry.getName())) {
                int wins = Integer.parseInt(entry.getElementsByTagName("wins").item(0).getTextContent()) + hsEntry.getWins();
                int losses = Integer.parseInt(entry.getElementsByTagName("losses").item(0).getTextContent()) + hsEntry.getLosses();
                int bestScore = Integer.parseInt(entry.getElementsByTagName("bestScore").item(0).getTextContent());
                if (hsEntry.getBestScore() > bestScore)
                        bestScore = hsEntry.getBestScore();

                entry.getElementsByTagName("wins").item(0).setTextContent(Integer.toString(wins));
                entry.getElementsByTagName("losses").item(0).setTextContent(Integer.toString(losses));
                entry.getElementsByTagName("bestScore").item(0).setTextContent(Integer.toString(bestScore));
                found = true;
            }
        }

        if (!found) {
            Element entry = doc.createElement("entry");
            element.appendChild(entry);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(hsEntry.getName()));
            entry.appendChild(name);

            Element wins = doc.createElement("wins");
            wins.appendChild(doc.createTextNode(Integer.toString(hsEntry.getWins())));
            entry.appendChild(wins);

            Element losses = doc.createElement("losses");
            losses.appendChild(doc.createTextNode(Integer.toString(hsEntry.getLosses())));
            entry.appendChild(losses);

            Element bestScore = doc.createElement("bestScore");
            bestScore.appendChild(doc.createTextNode(Integer.toString(hsEntry.getBestScore())));
            entry.appendChild(bestScore);
        }

        saveXML(doc);
    }

    public List<HighScoreEntry> getEntries() {
        List<HighScoreEntry> result = new ArrayList<HighScoreEntry>();
        Document doc = loadXML();

        if (doc != null) {
            Element element = doc.getDocumentElement();
            NodeList entries = element.getElementsByTagName("entry");

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);
                HighScoreEntry hsEntry = new HighScoreEntry();

                hsEntry.setName(entry.getElementsByTagName("name").item(0).getTextContent());
                hsEntry.setWins(Integer.parseInt(entry.getElementsByTagName("wins").item(0).getTextContent()));
                hsEntry.setLosses(Integer.parseInt(entry.getElementsByTagName("losses").item(0).getTextContent()));
                hsEntry.setBestScore(Integer.parseInt(entry.getElementsByTagName("bestScore").item(0).getTextContent()));

                result.add(hsEntry);
            }
        }

        return result;
    }
}
