package edu.stanford.dlss.flipbook;

import java.io.*;
import java.net.URL;
import argo.jdom.*;
import argo.saj.*;

/**
 *
 * Fetches JSON from PURL service, parses and validates it and passes it to flipbook
 *
 */
public class PagesMetadataJSON {
  private String druid;
  private String URL;
  private String json;
  private boolean isStanfordOnly = true;
  private final String server = "https://purl.stanford.edu";


  /**
   * Sets all member variables
   *
   * @param druid
   */
  public PagesMetadataJSON(String druid) {
    this.druid = druid;

    setURL();
    setIsStanfordOnly(setJSON());
  }


  /**
   * Fetches JSON from PURL service, validates and sets the json variable
   *
   * @return JsonRootNode of parsed JSON
   */
  private JsonRootNode setJSON() {
	JsonRootNode jsonRootNode = null;

    if (this.URL != null && this.URL.length() != 0) {
      try {
        JdomParser JDOM_PARSER = new JdomParser();
        StringBuffer response = new StringBuffer();

        URL serviceURL = new URL(this.URL);
        InputStream is = serviceURL.openStream();
        InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isReader);
        String dataLine = br.readLine();

        while (dataLine != null) {
          response.append(dataLine);
          dataLine = br.readLine();
        }

        this.json = response.toString();
        jsonRootNode = JDOM_PARSER.parse(this.json);
      }
      catch (IOException ioe) {
      	this.json = "Invalid JSON";
        System.err.println(ioe);
      }
      catch (InvalidSyntaxException ise) {
        this.json = "Invalid JSON";
      	System.err.println(ise);
      }
    }

    return jsonRootNode;
  }


  /**
   * Returns if the druid is for Stanford only view or not
   *
   * @return boolean
   */
  public boolean isStanfordOnly() {
    return this.isStanfordOnly;
  }


  /**
   * Sets isStanfordOnly value based on the 'readGroup' value from JSON
   *
   * @param jsonRootNode
   */
  private void setIsStanfordOnly(JsonRootNode jsonRootNode) {
  	if (jsonRootNode != null) {
  	  String readGroup = jsonRootNode.getStringValue("readGroup");
  	  this.isStanfordOnly = readGroup.equalsIgnoreCase("stanford");
  	}
  }


  /**
   * Returns validated JSON string
   *
   * @return String
   */
  public String getJSON() {
    return this.json;
  }


  /**
   * Sets flipbook JSON URL for fetching content
   *
   */
  private void setURL() {
    if (this.druid != null && this.druid.length() > 0) {
      this.URL = this.server + "/" + this.druid + ".flipbook";
    }
  }

}
