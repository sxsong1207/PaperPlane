package xyz.sxsong.paperplane.LibraryActivity.Notes

import xyz.sxsong.paperplane.ZoteroAPI.Model.Note

interface NoteInteractionListener {
    fun deleteNote(note: Note)
    fun editNote(note: Note)
}