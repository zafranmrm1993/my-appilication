package com.example.assessmentproject.model

data class TemperListModel(var data : ArrayList<Data>)
data class Data(var id : String,var job : Job,var starts_at:String,var ends_at:String,var earnings_per_hour : EarningsPerHour)
data class EarningsPerHour(var amount : Double,var currency : String)
data class Job(var title: String, var project : Project,var category : Category,var report_at_address : ReportAtAddress)
data class Project(var id : String,var name : String,var archived_at : String,var client : Client)
data class Client(var id : String,var name : String,var slug : String,var registration_name : String,
                  var registration_id : String,var description : String,var allow_temper_trial :Boolean,var links : Links,var rating : Rating)
data class Links(var hero_image : String,var thumb_image : String)
data class Rating(var count : String,var average : String)
data class Category(var id : String,var internalId : String,var name : String)
data class ReportAtAddress(var zip_code : String, var street : String, var number : String,
                           var number_with_extra : String, var extra : String, var city : String, var line1 : String
                           , var line2 : String, var links : LinksGeo, var geo : Geo)
data class LinksGeo(var get_directions : String)
data class Geo(var lat : Double,var lon : Double)

data class User(var name : String,var password : String,var address : String)
