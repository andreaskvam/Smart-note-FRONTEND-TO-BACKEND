package com.company;

import Files.Note;
import express.Express;
import express.middleware.Middleware;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	Database db = new Database();
	Express app = new Express();

	    app.get("/rest/notes", (request, response) -> {
            List<Note> notes = db.getNotes();
            response.json(notes);
        });

	    //Updaatera följande så att det ser mer ut som Johans POST, Se dock till att den kan hantera felr filer inom samma post
	    app.post("/rest/notes", (request, response) -> {
	        Note note = (Note)request.getBody(Note.class);
	        db.createNote(note);
            System.out.println(note.toString() + "created");
            response.send("post Ok");
        });

	    app.delete("/rest/notes/:id", (request, response) -> {
	        Note note = (Note)request.getBody(Note.class);
	        db.deleteNote(note);
            System.out.println(note.toString() + "deleted");
            response.send("delete Ok");
        });

	    app.put("/rest/notes/:id", (request, response) -> {
	        Note note = (Note) request.getBody(Note.class);
	        db.updateNoteById(note);
            System.out.println(note.toString() + "updated");
            response.send("Note was updated succesfully");
        });

        try {
            app.use(Middleware.statics(Paths.get("src/Frontend").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Listen for requests
        app.listen(7000);
        System.out.println("Server started on port: " + 7000);

    }
}
