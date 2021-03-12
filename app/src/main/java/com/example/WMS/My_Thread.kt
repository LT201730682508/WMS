package com.example.WMS

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class My_Thread {
    companion object{
        fun new_thread(performUi: perform_UI,executeIo: execute_IO){
            GlobalScope.launch (Dispatchers.Main){
                do_in_IO(executeIo)
                performUi.show()
            }
        }
        suspend fun do_in_IO(executeIo: execute_IO) = withContext(Dispatchers.IO) {
            executeIo.execute()
        }
    }
}
interface perform_UI{
    fun show()
}
interface execute_IO{
    fun execute()
}
