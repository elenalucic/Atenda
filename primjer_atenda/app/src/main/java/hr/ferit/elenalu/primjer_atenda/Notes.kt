package hr.ferit.elenalu.primjer_atenda

data class Notes( var id:String,
                  var notesName :String,
                  var addNote :String?= null
){
    constructor(): this("","", "")
}
