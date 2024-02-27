package org.books;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Books b1 = new Books("Hello world","Learn coding","Stina K","Educational", 2002);
        addRecord(b1);
        addRecord(new Books("Mary","Mary in Stockholm","Minna M","Horror", 1999));
        //getAllRecords();
        getRecordById("4");
    }

    public static void getAllRecords() {
        System.out.println("====== All Records ========");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Specify the URL from which you want to read JSON data
            URL url = new URL("http://localhost:8080/books");

            List<Books> books = objectMapper.readValue(url,
                    new TypeReference<List<Books>>() {});

            for (Books book : books) {
                System.out.println("Title: " + book.getTitle() +
                        ", Description: " + book.getDescription() +
                        ", Author: " + book.getAuthor() +
                        ", Category: " + book.getCategory() +
                        ", Year Published: " + book.getYearPublished());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class Record {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("author")
        private String author;

        @JsonProperty("category")
        private String category;

        @JsonProperty("yearPublished")
        private int yearPublished;

        public Record() {
        }

        public Record(String title, String description, String author, String category, int yearPublished) {
            this.title = title;
            this.description = description;
            this.author = author;
            this.category = category;
            this.yearPublished = yearPublished;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getYearPublished() {
            return yearPublished;
        }

        public void setYearPublished(int yearPublished) {
            this.yearPublished = yearPublished;
        }
    }
    public static void getRecordById(String sid) {
        System.out.println("====== Record by id ========");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Specify the URL from which you want to read JSON data
            URL url = new URL("http://localhost:8080/books/" + sid);

            // Open a connection to the URL and read the JSON data
            JsonNode jsonNode = objectMapper.readTree(url);

            // Process the JSON data as needed
            System.out.println(jsonNode);

            // Example: Accessing specific fields from JSON
            String title = jsonNode.get("title").asText();
            String description = jsonNode.get("description").asText();
            String author = jsonNode.get("author").asText();
            String category = jsonNode.get("category").asText();
            int yearPublished = jsonNode.get("yearPublished").asInt();

            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Author: " + author);
            System.out.println("Category: " + category);
            System.out.println("Year Published: " + yearPublished);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRecord(Books newBook) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serialize the object to JSON and print it to the console
            String jsonInString = objectMapper.writeValueAsString(newBook);
            System.out.println(jsonInString);

            String postUrl = "http://localhost:8080/books";

            // Create an HttpClient instance
            HttpClient httpClient = HttpClients.createDefault();

            // Create an HttpPost with the URL
            HttpPost httpPost = new HttpPost(postUrl);

            // Set the Content-Type header
            httpPost.setHeader("Content-Type", "application/json");

            // Set the JSON payload
            StringEntity entity = new StringEntity(jsonInString);
            httpPost.setEntity(entity);

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPost);

            // Print the response code
            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Books {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("author")
        private String author;

        @JsonProperty("category")
        private String category;

        @JsonProperty("yearPublished")
        private int yearPublished;

        public Books() {
        }

        public Books(String title, String description, String author, String category, int yearPublished) {
            setTitle(title);
            setDescription(description);
            setAuthor(author);
            setCategory(category);
            setYearPublished(yearPublished);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getYearPublished() {
            return yearPublished;
        }

        public void setYearPublished(int yearPublished) {
            this.yearPublished = yearPublished;
        }
    }
}