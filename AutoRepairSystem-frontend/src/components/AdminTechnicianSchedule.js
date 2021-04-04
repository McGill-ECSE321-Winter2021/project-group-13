import AdminNavbar from '@/components/AdminNavbar';
import VueTimepicker from 'vue2-timepicker';
import 'vue2-timepicker/dist/VueTimepicker.css';

import axios from "axios";
var config = require("../../config");
var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
});

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

function getEndBreak(startBreak) {
    var endBreakHH = parseInt(startBreak.HH);
    endBreakHH = endBreakHH + 1;
    var endBreakString = endBreakHH.toString() + ":" + startBreak.mm + ":00";

    if(endBreakHH < 10) {
      endBreakString = "0" + endBreakString; // add leading 0 for correct time formatting ("H:mm:ss" is invalid)
    }

    return endBreakString;

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
      breakTime: {HH: "",mm: ""},
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

    breakTimeInput(eventData) {
      this.breakTime = eventData;
    },
    
    createBusinessHour(selectedCol) {
      var dateString = formatDate(addDays(date,selectedCol));
      var startTimeString = this.businessHourStart.HH + ":" + this.businessHourStart.mm + ":00";
      var endTimeString = this.businessHourEnd.HH + ":" + this.businessHourEnd.mm + ":00";
      
      AXIOS.post("/businesshours/" + dateString + "?start=" + startTimeString + "&end=" + endTimeString)
        .then(response => {
          this.getAllBusinessHours();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
        
    },

    updateBusinessHour(selectedBusinessHour) {

      var startTimeString;
      var endTimeString;

      if(businessHourStart === "") {
        startTimeString = selectedBusinessHour.startTime;
      } else {
        startTimeString = + this.businessHourStart.HH + ":" + this.businessHourStart.mm + ":00";
      }

      if(businessHourEnd === "") {
        endTimeString = selectedBusinessHour.endTime;
      } else {
        endTimeString =  + this.businessHourEnd.HH + ":" + this.businessHourEnd.mm + ":00";
      }
      
      AXIOS.put("/businesshours/" + selectedBusinessHour.id + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + selectedBusinessHour.date)
        .then(response => {
          this.getAllBusinessHours();
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
    },

    deleteBusinessHour(selectedBusinessHour) {
      
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
      var startTimeString = this.technicianHourStart.HH.toString() + ":" + this.technicianHourStart.mm + ":00";
      var endTimeString =  this.technicianHourEnd.HH.toString() + ":" + this.technicianHourEnd.mm + ":00";
      var startBreakString = this.breakTime.HH + ":" + this.breakTime.mm + ":00";
      
      var lunchBreak;
      var endBreakString;
      
      if(this.breakTime.HH === "" || this.breakTime.mm === "") {
        lunchBreak = false;
      } else {
        lunchBreak = true;
        endBreakString = getEndBreak(this.breakTime);
      }

      // Get end break time (lunch break is always one hour)
      
      
      AXIOS.post("/technicianhours/" + selectedTechnician.username + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + dateString)
        .then(response => {
          if(lunchBreak) {
            console.log(response.data.id);
            AXIOS.post("/workbreaks/" + response.data.id + "?startBreak=" + startBreakString + "&endBreak=" + endBreakString)
              .then(response => {
              })
              .catch(e => {
                this.errorMessage = e.response.data;
              });
          } 
        })
        .catch(e => {
          this.errorMessage = e.response.data;
        });
      this.getAllTechnicians;
    },

    updateTechnicianHour(selectedTechnicianHour) {
      var startTimeString;
      var endTimeString;
      var startBreakString;
      var lunchBreak;
      var newLunchBreak = false;

      if(this.technicianHourStart.HH === "" || this.technicianHourStart.mm === "") {
        startTimeString = selectedTechnicianHour.startTime;
      } else {
        startTimeString = + this.technicianHourStart.HH + ":" + this.technicianHourStart.mm + ":00";
      }

      if(this.technicianHourEnd.HH === "" || this.technicianHourEnd.mm === "") {
        endTimeString = selectedTechnicianHour.endTime;
      } else {
        endTimeString = this.technicianHourEnd.HH + ":" + this.technicianHourEnd.mm + ":00";
      }

      if(this.breakTime.HH === "" || this.breakTime.mm === "") {
        lunchBreak = false;
      } else {
        startBreakString = this.breakTime.HH + ":" + this.breakTime.mm + ":00";
        lunchBreak = true;
        var endBreakString = getEndBreak(this.breakTime);

        if(typeof(selectedTechnicianHour.workBreaks[0]) == 'undefined') {
          newLunchBreak = true;
        }
      }
      AXIOS.put("/technicianhours/" + selectedTechnicianHour.id + "?start=" + startTimeString + "&end=" + endTimeString + "&date=" + selectedTechnicianHour.date)
        .then(response => {
          if(lunchBreak) {
            if(newLunchBreak) {
              AXIOS.post("/workbreaks/" + selectedTechnicianHour.id + "?startBreak=" + startBreakString + "&endBreak=" + endBreakString)
              .then(response => {
                this.getAllTechnicians();
              })
              .catch(e => {
                this.errorMessage = e.response.data;
              });
            } else {
            AXIOS.put("/workbreaks/" + selectedTechnicianHour.workBreaks[0].id + "?startBreak=" + startBreakString + "&endBreak=" + endBreakString)
              .then(response => {
                this.getAllTechnicians();
              })
              .catch(e => {
                this.errorMessage = e.response.data;
              });
            }
          } else {
          this.getAllTechnicians();
          }
        })
        .catch(e => {
          this.errorMessage = e;
          this.getAllTechnicians();
        });
    },

    deleteTechnicianHour(selectedTechnicianHour) {
      
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