package com.hussein.mynote.data.repository
import com.hussein.mynote.data.local.dao.NoteDao
import com.hussein.mynote.model.Note
import com.hussein.mynote.model.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryNote @Inject constructor(
    private val noteDao: NoteDao
) {
    //var allNotes = emptyList<Note>()
    //fun allNotes() = noteDao.getAllNotes()
    //var foundNote = MutableLiveData<Note>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addNote(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.addNote(note)
        }
    }
    suspend fun getAllNotes() : Flow<ResourceState<List<Note>>>{
      return flow {
           emit(ResourceState.Loading())
           val allNotes = noteDao.getAllNotes()
          if (!allNotes.isNullOrEmpty()) {
              emit(ResourceState.Success(allNotes))
          }
          else
          {
              emit(ResourceState.Error("Error , No Data Founded"))
          }
       }.catch { e->
          emit(ResourceState.Error(e.localizedMessage?:"Some Error in flow"))
      }
    }
    fun deleteNote(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note.id)
            getAllNotes()
        }
    }

}