<template>
	<div>
    <div id = "navbar-container">
      <AdminNavbar/>
    </div>
    <div class="side-menu">
      <div class="businessHourMenu">
        <h2>Manage <br>Business Hours</h2>
        <div class = "createBusinessHour" v-if="selectedBusinessHourId <= 0 || selectedBusinessHourId == null">
          <div class = "time-pick">
          <p class = "left-pick"> Open Time:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startBusinessHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Close Time:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endBusinessHourInput"></vue-timepicker>
          </p>
          </div>
          <div class ="button-container">
            <button
            v-bind:disabled="(selectedBusinessHour == null || businessHourStart.HH == '' || businessHourStart.mm == '' ||  businessHourEnd.HH == '' || businessHourEnd.mm == '')" 
            v-on:click="createBusinessHour(selectedBusinessHour.col)" 
            >
              Create Business Hour
            </button>
          </div>
        </div>
          <div class = "createBusinessHour" v-if="selectedBusinessHourId > 0">
            <div class = "time-pick">
            <p class = "left-pick"> New Open Time:</p> 
            <p class = "right-pick" > 
              <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startBusinessHourInput"></vue-timepicker>
            </p>
            </div>
            <div class = "time-pick">
            <p class = "left-pick"> New Close Time:</p> 
            <p class = "right-pick" > 
              <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endBusinessHourInput"></vue-timepicker>
            </p>
            </div>
            <div>
            <div class = "button-container" v-if="selectedBusinessHourId > 0" >
              <button style ="margin-bottom: 10px"
              v-bind:disabled="(selectedBusinessHour == null || businessHourStart.HH == '' || businessHourStart.mm == '' ||  businessHourEnd.HH == '' || businessHourEnd.mm == '')" 
              v-on:click="updateBusinessHour(selectedBusinessHour)" 
              >
                Update Business Hour
              </button>
              <button
              v-bind:disabled="(selectedBusinessHour == null)"
              v-on:click="deleteBusinessHour(selectedBusinessHour)"
              >
                Delete Business Hour
              </button>
            </div>
          </div>
        </div>
      </div> <!-- close business hour window -->
      <div class="technicianHourWindow">
        <h2>Manage <br> Technician Shifts</h2>
        <div class = "createTechnicianHour" v-if="selectedTechnicianHourId <= 0 || selectedTechnicianHourId == null">
          <div class = "time-pick">
          <p class = "left-pick"> Shift Start :</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Shift End:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div>
            <button
            v-bind:disabled="(selectedTechnicianHour == null || technicianHourStart.HH == '' || technicianHourStart.mm == '' ||  technicianHourEnd.HH == '' || technicianHourEnd.mm == '')" 
            v-on:click="createTechnicianHour(selectedTechnicianHour.col,selectedTechnician)" 
            >
              Schedule Shift
            </button>
          </div>
        </div>
          <div class = "updateTechnicianHour" v-if="selectedTechnicianHourId > 0">
          <div class = "time-pick">
          <p class = "left-pick"> Updated Shift Start :</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Updated Shift End:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div>
            <div v-if="selectedTechnicianHourId > 0" >
              <button
              v-bind:disabled="(selectedTechnicianHour == null || technicianHourStart.HH == '' || technicianHourStart.mm == '' ||  technicianHourEnd.HH == '' || technicianHourEnd.mm == '')" 
              v-on:click="updateTechnicianHour(selectedTechnicianHour)" 
              >
                Update Shift
              </button>
              <button
              v-bind:disabled="(selectedTechnicianHour == null)"
              v-on:click="deleteTechnicianHour(selectedTechnicianHour)"
              >
                Delete Shift
              </button>
            </div>
          </div>
        </div>
      </div> <!-- close technician hour window -->
    </div> <!-- close sidebar -->
		<div class="calendar">
    <h2>Technician Schedule</h2>
      <div id="month-header"><span id="month">{{month}} {{year}}</span></div>
      <div id="week-buttons"> 
        <button class="week-change" v-on:click="changeWeek(false)">
          Previous
        </button>
        <button class="week-change" v-on:click="changeWeek(true)">
          Next  
        </button>
      </div>
      <table id="hours-table">
        <tr id="calendar-top-row">
          <td></td>
          <td>Sun.<br>{{weekdays[0]}}</td>
          <td>Mon.<br>{{weekdays[1]}}</td>
          <td>Tue.<br>{{weekdays[2]}}</td>
          <td>Wed.<br>{{weekdays[3]}}</td>
          <td>Thu.<br>{{weekdays[4]}}</td>
          <td>Fri.<br>{{weekdays[5]}}</td>
          <td>Sat.<br>{{weekdays[6]}}</td>
        </tr>
        <tr id="business-hour-row">  
          <td class ="row-header"> Business Hours </td>
          <td class="hour-slot" 
          v-for="businessHour in businessHoursWeek" v-bind:key="businessHour.id" 
          @click="selectBusinessHour(businessHour)" 
          :class="{'highlight': (businessHour.id == selectedBusinessHourId)}" >
            <div v-if="businessHour.id > 0" >
            open: {{ formatTimeDisplay(businessHour.startTime) }}<br> 
            close: {{ formatTimeDisplay(businessHour.endTime) }}
            </div>
            <div v-if="businessHour.id < 0">
              CLOSED
            </div>
          </td>
          
        </tr>
        <tr class="technicianRows" v-for="(technician,index) in technicians" v-bind:key="technician.username">
          <td> {{technician.name}} </td>
          <td class="hour-slot" 
          v-for="technicianHour in technicianHoursWeek[index]" v-bind:key="technicianHour.id" 
          @click="selectTechnicianHour(technicianHour), selectTechnician(technician)" 
          :class="{'highlight': (technicianHour.id == selectedTechnicianHourId)}" >
            <div >
              <div v-if="technicianHour.id > 0">
                {{ formatTimeDisplay(technicianHour.startTime) }} <span>&ndash;</span> {{ formatTimeDisplay(technicianHour.endTime) }}
              </div>
              <div v-if="technicianHour.id < 0">
                X
              </div>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<style>

#business-hour-row {
  height: 60px;
  border: 1px solid black;
}

.button-container {
  height: 80px;
}

.row-header {
  text-align: center;
  font-weight: bolder;
}

.time-pick {
  height: 60px;
}

.left-pick {
  position:relative; top:5px;
  float: left;
  margin-left: 5px;
}

.right-pick {
  float: right;
  margin-right: 5px;
}

.hour-slot {
  border: 1px solid black;
}

.technicianRows {
  height: 60px;
  border: 1px solid black;
}
.side-menu {
  background-color: #353A57;
  width: 280px;
  height: 100vh;
  float: left;
  padding: 10px;
  color: white;
}

.calendar {
  background-color: #CDD7DE;
  width: calc(100% - 280px);
  height: 100vh;
  float: right;
  padding: 10px;
}

#header-appointments {
  background: white;
}

.datetime-block {
  margin-top: 30px;
  text-align: left;
  font-size: 20px;
}

.datetime-block h3 {
  text-align: center;
}

.back {
  position: absolute;
    bottom: 10px;
    left: 0%;
    margin-left: 10px;
}

#month-header {
  font-size: 25px;
  float: left;
}

#week-buttons {
  float: right;
  margin-bottom: 5px;
}

.highlight {
 background: yellow;
}

button.week-change {
  margin-left: 5px;
  margin-right: 5px;
  width: 100px;
  font-size: 20px;
}

#hours-table {
  width: 100%;
  table-layout: fixed;
}

#calendar-top-row, #calendar-top-row td {
  border: 1px solid black;
  text-align: center;
}

#calendar-top-row {
  background-color: white;
  font-size: 20px;
  height: 80px;
}

body {
  overflow: hidden; /*This stops the page from having a scroll bar (might be able to remove this later)*/
}

#app {
  margin-top: 0;
}

.navbar-container {
  margin-bottom: 0px;
}

table {
  empty-cells: show;
}

</style>

<script>
import AdminNavbar from '@/components/AdminNavbar'
import VueTimepicker from 'vue2-timepicker'
import 'vue2-timepicker/dist/VueTimepicker.css'

import axios from "axios";
var config = require("../../config");
var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
})



//Variable which stores the date of the start of the displayed week (Sunday)
var date = new Date();
date.setDate(date.getDate()-date.getDay());

var fakeIdCounter = -1;

function incrementFakeIdCounter() {
  fakeIdCounter--;
}

class fakeWorkHour {
  constructor(col) {
    this.id = fakeIdCounter;
    this.col = col;
    incrementFakeIdCounter();
  }
}

//This function returns the month displayed (mid-week day's month)
function getDisplayedMonth() {
  var midWeek = new Date(date);
  midWeek.setDate(midWeek.getDate()+3);
  return midWeek.toLocaleString('default', { month: 'long' });
}

//This function returns the year displayed (mid-week day's year)
function getDisplayedYear() {
  var midWeek = new Date(date);
  midWeek.setDate(midWeek.getDate()+3);
  return midWeek.getFullYear();
}

//This function returns the day of the week displayed
function getWeekdays() {
  var datesOfWeek = ["", "", "", "", "", "", ""];

  var day = new Date(date);
  datesOfWeek[0] = date.getDate();
  for (var i=1;i<7;i++) {
    day.setDate(day.getDate()+1);
    datesOfWeek[i] = day.getDate();
  }

  return datesOfWeek;
}

function formatDate(aDate) {
  var offset = aDate.getTimezoneOffset()*60000; // timezone offset in milliseconds
  var formattedDate = (new Date(aDate - offset)).toISOString().slice(0,10);
  return formattedDate;
}

function addDays(aDate,numDays) {
  var newDate = new Date(aDate);
  newDate.setDate(aDate.getDate() + numDays);
  return newDate;
}

export default {
  name: "AdminTechnicianSchedule",
  data () {
    return {
      errorMessage: "",
      month: "",
      year: "",
      weekdays: "",
      inputDate: "",
      inputTime: "",
      technicians: [],
      numTechnicians: 0,
      technicianHoursWeek: [],
      businessHours: [],
      businessHoursWeek: [],
      selectedBusinessHour: "",
      selectedBusinessHourId: "",
      selectedTechnicianHour: "",
      selectedTechnicianHourId: "",
      selectedTechnician: "",

      businessHourStart: {HH: "", mm: ""},
      businessHourEnd: {HH: "", mm: ""},
      technicianHourStart: {HH: "", mm: ""},
      technicianHourEnd: {HH: "",mm: ""},
    }
  },

  components: {
    AdminNavbar,
    VueTimepicker
  },

  created: function () {
    this.getAllTechnicians();
    this.getAllBusinessHours();
    this.month = getDisplayedMonth();
    this.year = getDisplayedYear();
    this.weekdays = getWeekdays();
    },

  methods: {

    //This method changes the displayed week and related information
    changeWeek: function(addDays) {
      if (addDays) date.setDate(date.getDate()+7);
      else date.setDate(date.getDate()-7)
      this.year = getDisplayedYear();
      this.month = getDisplayedMonth();
      this.weekdays = getWeekdays();
      this.businessHoursWeek = this.getBusinessHoursWeek();
    },

    getAllTechnicians: function() {
      AXIOS.get("/technicians/")
      .then(response => {
        this.technicians = response.data;
        if(typeof(this.technicians) !== 'undefined') {
          this.numTechnicians = this.technicians.length;
        }

        this.sortTechniciansByName();
        this.getTechnicianHoursWeek();
      })
      .catch(e => {
        this.errorMessage = e.response.data;
      })
    },

    getTechnicianHoursWeek: function() {
      for(var i = 0; i < this.numTechnicians; i++) { // iterating over every technician
        var tempTechnicianHours = this.technicians[i].technicianHours; 
        var tempTechnicianHoursWeek = new Array(7).fill(null);
        for(var j = 0; j < 7; j++) { // iterating over each day of the week
          if(typeof(tempTechnicianHours) !== 'undefined') {
            for(var k = 0; k < tempTechnicianHours.length; k++) { // iterating over each of the technician's hours to see if it falls on the current day of the week
              if(tempTechnicianHours[k].date === formatDate(addDays(date,j))) { // date variable is a global variable that stores date of first day of displayed week
                tempTechnicianHoursWeek[j] = tempTechnicianHours[k];
              }
            }
          }
          if(tempTechnicianHoursWeek[j] == null) {
            tempTechnicianHoursWeek[j] = new fakeWorkHour(j);
          }
        }
        this.technicianHoursWeek[i] = tempTechnicianHoursWeek;
      }
    },

    getAllBusinessHours: function() {
      AXIOS.get('/businesshours/')
      .then(response => {
        this.businessHours = response.data;
        this.businessHoursWeek = this.getBusinessHoursWeek();
      })
      .catch(e => {
        this.errorMessage = e.response.data;
      });
    },

    getBusinessHoursWeek: function() {
      var tempBusinessHoursWeek = new Array();
      if(typeof(this.businessHours) !== 'undefined') {
        for(var i = 0; i < 7; i++) {
          var numBusinessHours = this.businessHours.length;
          for(var j = 0; j < numBusinessHours; j++) {
            if(this.businessHours[j].date === formatDate(addDays(date,i))) {
              tempBusinessHoursWeek[i] = this.businessHours[j];
            }
          }
        }
      }
      for (var i = 0; i < 7; i++ ) {
        if(typeof(tempBusinessHoursWeek[i]) == 'undefined') {
          tempBusinessHoursWeek[i] = new fakeWorkHour(i);
        }
      }
      return tempBusinessHoursWeek;
    },

    //This method takes the user back to the customer home page
    backButton: function() {
      this.$router.push({name: "CustomerHome"});
    },

    //This method converts date input field to a date object (Where we only care about the date)
    getDateClean(currDate) {
        if (currDate == "") return "";
        var selectedDate = new Date(currDate + "T00:00:00");
        return selectedDate;
    },

    //This method converts time input field to a date object (Where we only care about the time)
    getTimeClean(currTime) {
      if (currTime == "") return "";
      var selectedTime = new Date();
      var time = currTime.split(":");
      selectedTime.setHours(time[0], time[1], "00");
      return selectedTime;
    },

    selectBusinessHour(businessHour) {
      this.selectedBusinessHour = businessHour;
      this.selectedBusinessHourId = businessHour.id;
      this.selectedTechnicianHour = "";
      this.selectedTechnicianHourId = "";
    },

    selectTechnicianHour(technicianHour) {
      this.selectedTechnicianHour = technicianHour;
      this.selectedTechnicianHourId = technicianHour.id;
      this.selectedBusinessHour = "";
      this.selectedBusinessHourId = "";
    },

    selectTechnician(technician) {
      this.selectedTechnician = technician;
    },

    startBusinessHourInput(eventData) {
      this.businessHourStart = eventData;
    },

    endBusinessHourInput(eventData) {
      this.businessHourEnd = eventData;
    },

    startTechnicianHourInput(eventData) {
      this.technicianHourStart = eventData;
    },

    endTechnicianHourInput(eventData) {
      this.technicianHourEnd = eventData;
    },

    createBusinessHour(selectedCol) {
      var dateString = formatDate(addDays(date,selectedCol));
      var startTimeString = + this.businessHourStart.HH + ":" + this.businessHourStart.mm + ":00";
      var endTimeString =  + this.businessHourEnd.HH + ":" + this.businessHourEnd.mm + ":00";
      
      AXIOS.post("/businesshours/" + dateString + "?start=" + startTimeString + "&end=" + endTimeString)
        .then(response => {
          this.getAllBusinessHours();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
        
    },

    updateBusinessHour(selectedBusinessHour) {

      var startTimeString = + this.businessHourStart.HH + ":" + this.businessHourStart.mm + ":00";
      var endTimeString =  + this.businessHourEnd.HH + ":" + this.businessHourEnd.mm + ":00";
      
      AXIOS.put("/businesshours/" + selectedBusinessHour.id + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + selectedBusinessHour.date)
        .then(response => {
          this.getAllBusinessHours();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
    },

    deleteBusinessHour(selectedBusinessHour) {

      var startTimeString = + this.businessHourStart.HH + ":" + this.businessHourStart.mm + ":00";
      var endTimeString =  + this.businessHourEnd.HH + ":" + this.businessHourEnd.mm + ":00";
      
      AXIOS.delete("/businesshours/" + selectedBusinessHour.id)
        .then(response => {
          this.getAllBusinessHours();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
      this.selectedBusinessHour = null;
      this.selectedBusinessHourId = null;
    },

    createTechnicianHour(selectedCol,selectedTechnician) {
      var dateString = formatDate(addDays(date,selectedCol));
      var startTimeString = + this.technicianHourStart.HH + ":" + this.technicianHourStart.mm + ":00";
      var endTimeString =  + this.technicianHourEnd.HH + ":" + this.technicianHourEnd.mm + ":00";
      
      AXIOS.post("/technicianhours/" + selectedTechnician.username + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + dateString)
        .then(response => {
          this.getAllTechnicians();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
        
    },

    updateTechnicianHour(selectedTechnicianHour) {

      var startTimeString = + this.technicianHourStart.HH + ":" + this.technicianHourStart.mm + ":00";
      var endTimeString =  + this.technicianHourEnd.HH + ":" + this.technicianHourEnd.mm + ":00";
      
      AXIOS.put("/technicianhours/" + selectedTechnicianHour.id + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + selectedTechnicianHour.date)
        .then(response => {
          this.getAllTechnicians();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
    },

    deleteTechnicianHour(selectedTechnicianHour) {

      var startTimeString = + this.technicianHourStart.HH + ":" + this.technicianHourStart.mm + ":00";
      var endTimeString =  + this.technicianHourEnd.HH + ":" + this.technicianHourEnd.mm + ":00";
      
      AXIOS.delete("/technicianhours/" + selectedTechnicianHour.id)
        .then(response => {
          this.getAllTechnicians();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
      this.selectedTechnicianHour = null;
      this.selectedTechnicianHourId = null;
    },

    formatTimeDisplay(aTime) {
      var str = aTime;
      str = str.slice(0,5);
      if(str.charAt(0) === "0") {
        str = str.slice(1,5);
      }
      return str;
    },

    sortTechniciansByName() { // using insertion sort
      var sortedTechnicians = new Object(this.technicians);
      if(this.numTechnicians > 0) {
        for (var i = 1; i < this.numTechnicians; i++) {
          var current = sortedTechnicians[i];
          var j = i-1;
          while((j > - 1) && (current.name < sortedTechnicians[j].name)) {
            sortedTechnicians[j+1] = sortedTechnicians[j];
            j--;
          }
          sortedTechnicians[j+1] = current;
        }
      }
      this.technicians = sortedTechnicians;
    },
  }
}

</script>