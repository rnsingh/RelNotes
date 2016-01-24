package org.jenkinsci.plugins.releaseNotes;

//package com.atlassian.api.examples;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;




import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



/**
 * Demonstrates how to update a page using the Confluence 5.5 REST API.
 */
public class ReleaseNotesWikiUpdate
{
    private static final String BASE_URL = "https://rnsingh.atlassian.net/wiki";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Singh8450";
    private static final String ENCODING = "UTF-8";
    //public static String Message = "12345" + PASSWORD ;

    private static String getContentRestUrl(final Long contentId, final String[] expansions) throws UnsupportedEncodingException
    {
        final String expand = URLEncoder.encode(StringUtils.join(expansions, ","), ENCODING);

        return String.format("%s/rest/api/content/%s?expand=%s&os_authType=basic&os_username=%s&os_password=%s", BASE_URL, contentId, expand, URLEncoder.encode(USERNAME, ENCODING), URLEncoder.encode(PASSWORD, ENCODING));
    }

    public  void updateWiki(String message) throws Exception
    {
        final long pageId = 1572866;

        HttpClient client = new DefaultHttpClient();

        // Get current page version
        String pageObj = null;
        HttpEntity pageEntity = null;
        try
        {
            HttpGet getPageRequest = new HttpGet(getContentRestUrl(pageId, new String[] {"body.storage", "version", "ancestors"}));
            HttpResponse getPageResponse = client.execute(getPageRequest);
            pageEntity = getPageResponse.getEntity();

            pageObj = IOUtils.toString(pageEntity.getContent());

            System.out.println("Get Page Request returned " + getPageResponse.getStatusLine().toString());
            System.out.println("");
            System.out.println(pageObj);
        }
        finally
        {
            if (pageEntity != null)
            {
                EntityUtils.consume(pageEntity);
            }
        }

        // Parse response into JSON
        
        
    
        JSONObject page = new JSONObject(pageObj);


        // Update page
        // The updated value must be Confluence Storage Format (https://confluence.atlassian.com/display/DOC/Confluence+Storage+Format), NOT HTML.
        page.getJSONObject("body").getJSONObject("storage").put("value", "Message");
       // page.getJSONObject("title").getJSONObject("storage").put("value", "Test Page");
        int currentVersion = page.getJSONObject("version").getInt("number");
        page.getJSONObject("version").put("number", currentVersion + 1);

        // Send update request
        HttpEntity putPageEntity = null;
      //  HttpEntity postPageEntity = null;

        try
        {
            HttpPut putPageRequest = new HttpPut(getContentRestUrl(pageId, new String[]{}));
          //  HttpPost postPageRequest = new HttpPost(BASE_URL);
            StringEntity entity = new StringEntity(page.toString(), ContentType.APPLICATION_JSON);
            putPageRequest.setEntity(entity);
         //   postPageRequest.setEntity(entity);

            HttpResponse putPageResponse = client.execute(putPageRequest);
            putPageEntity = putPageResponse.getEntity();
          //  HttpResponse postPageResponse = client.execute(postPageRequest);
          //  postPageEntity = postPageResponse.getEntity();
            
            System.out.println("Put Page Request returned " + putPageResponse.getStatusLine().toString());
            System.out.println("");
            System.out.println(IOUtils.toString(putPageEntity.getContent()));
         //   System.out.println("Post Page Request returned " + ((HttpResponse) postPageEntity).getStatusLine().toString());
         //   System.out.println("");
         //   System.out.println(IOUtils.toString(postPageEntity.getContent()));
        }
        finally
        {
            EntityUtils.consume(putPageEntity);
          //  EntityUtils.consume(postPageEntity);
        }
    }
}
