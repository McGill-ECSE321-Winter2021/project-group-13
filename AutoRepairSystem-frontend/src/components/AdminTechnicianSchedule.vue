<template>
	<div>
		<div class="calendar">
    	<h2>Availability Calendar</h2>
    		<div id="month-header"><span id="month">{{month}} {{year}}</span></div>
    			<div id="week-buttons"><button class="week-change" v-on:click="changeWeek(false)">Previous</button><button class="week-change" v-on:click="changeWeek(true)">Next  </button></div>
    				<table id="appointments-table">
      					<tr id="calendar-top-row">
							<td>Sun.<br>{{weekdays[0]}}</td>
							<td>Mon.<br>{{weekdays[1]}}</td>
							<td>Tue.<br>{{weekdays[2]}}</td>
							<td>Wed.<br>{{weekdays[3]}}</td>
							<td>Thu.<br>{{weekdays[4]}}</td>
							<td>Fri.<br>{{weekdays[5]}}</td>
							<td>Sat.<br>{{weekdays[6]}}</td>
      					</tr>
      				<tr id="available-slots">
        		<td>
					<div id="availability-block" v-for="availability in availabilities[0]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[1]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[2]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[3]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[4]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[5]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
				<td>
					<div id="availability-block" v-for="availability in availabilities[6]">{{availability.startTime}}<br>{{availability.endTime}}</div>
				</td>
      </tr>
    </table>
  </div>
</div>
</template>
<style>

.sidemenu {
  background-color: #353A57;
  width: 25%;
  height: 100vh;
  float: left;
  padding: 10px;
  color: white;
}

.calendar {
  background-color: #CDD7DE;
  width: 75%;
  height: 100vh;
  float: right;
  padding: 10px;
}

.service-table {
  overflow-y: auto;
  height: 30vh;
}

.service-table thead th {
  position: sticky;
  top: 0;
}

#service-table {
  border-collapse: collapse;
  width: 100%;
  background-color: #EAF0F4;
  color: black;
}

#service {
  margin-left: 10px;
}

#servicediv {
  margin-top: 5px;
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

#datetimepicker {
  margin-left: 10px;
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

button.week-change {
  margin-left: 5px;
  margin-right: 5px;
  width: 100px;
  font-size: 20px;
}

#appointments-table {
  width: 100%;
  table-layout: fixed;
}

#available-slots, #available-slots td, #calendar-top-row, #calendar-top-row td, #service-table tr, #service-table td {
  border: 1px solid black;
  text-align: center;
}

/*The rows of the appointment calendar:
*/
#calendar-top-row {
  background-color: white;
  font-size: 20px;
}

#available-slots {
  height: calc(100vh - 150px);
  background-color: #EAF0F4;
}

/*Availability blocks in the appointment calendar:
*/
#availability-block {
  background-color: white;
  width: 95%;
  border-radius: 50px;
  border: 1px solid black;
  margin: 0 auto;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 5px;
  margin-top: 5px;
}

body {
  overflow: hidden; /*This stops the page from having a scroll bar (might be able to remove this later)*/
}

#app {
  margin-top: 0;
}

#totals {
  text-align: left;
}

.navbarContainer {
  margin-bottom: 0px;
}

</style>

<script>
import AdminNavbar from '@/components/AdminNavbar'

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

export default {
  name: "AvailableAppointments",
  data () {
    return {
      errorMessage: "",
      month: "",
      year: "",
      weekdays: "",
      inputDate: "",
      inputTime: "",
      services: [],
      checkedServices: [],
      availabilities: [[], [], [], [], [], [], []],
    }
  },

  components: {
    CustomerNavbar
  },

  created: function () {
    AXIOS.get(`/workitems`)
        .then((response) => {
          this.services = response.data;
        })
        .catch((e) => {
          this.errorMessage = e.response.data;
        });

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
    },

    //This method takes the user back to the customer home page
    backButton: function() {
      this.$router.push({name: "CustomerHome"});
    },

    getTotalServicesPrice: function() {
      var totalPrice = 0;
      for (var i=0; i<this.checkedServices.length; i++) {
        totalPrice += this.checkedServices[i].price;
      }
      return totalPrice;
    },

    getTotalServicesDuration: function() {
      var totalDuration = 0;
      for (var i=0; i<this.checkedServices.length; i++) {
        totalDuration += this.checkedServices[i].duration;
      }
      return totalDuration;
    },

    createAppointment: function () {
      var dateString = this.inputDate.toLocaleString("default", { dateStyle: "full" });
      var timeString = this.inputTime.toLocaleString("default", { timeStyle: "long"});

      var servicesString = this.checkedServices[0].name;
      for (var i=1; i<this.checkedServices.length-1; i++) {
        servicesString += ", " + this.checkedServices[i].name;
      }
      servicesString += ", " + this.checkedServices[this.checkedServices.length-1].name + ".";

      var confirmation = window.confirm("Are you sure you want to book an appointment on " + dateString + " at " + timeString + " with the followin services: " + servicesString);

      if (confirmation) {
        //Call Axios and create the appointment (NOTE: remember to update the parameters of the function in the html too when adding this)
      }
    },

    getWeekAvailabilities: function() {
      //Returns the availabilities for every day of the week

      for (var i=0; i<7; i++) {
        //Call Axios here
      }

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
    }

  }
  
}



</script>