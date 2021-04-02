<template>
<div>

  <div class="navbarContainer">
      <AdminNavbar/>
  </div>
  
  <div id="home">
    <h2>Home (ADMIN) </h2>
  
    <div id="invoices">
      <button id="button2" @click="checkinvoices()">Check/Pay Outstanding Balances</button> 
       
      <button id="buttonlogout" @click="logout()">Log Out </button> 
    </div>

    <div id="currentappointments">
      <h2> Current Appointments </h2>
      <br>
      <div class="datetime-block">
        <label for="appt-date">Select a Date: </label><span id="datetimepicker"><input type="date" @input="getDateClean($event.target.value)"></span>
      </div>
      <table class="styled-table">
        <thead>
          <tr>
            <th>Technician</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appointment in appointments" @click="selectRow(appointment)" v-bind:key="appointment.id" :class="{'highlight': (appointment.id === selectedAppointment)}">
            <td>{{ appointment.technician.name }}</td>
            <td>{{ appointment.startTime }}</td>
            <td>{{ appointment.endTime }}</td>
          </tr>
        </tbody>
      </table>
    <br>
    <br>
    <button id="deletebutton" @click="deleteaccountpopup()">Delete Account</button>
    <br>
    </div>
  </div>
</div>
</template>


<style>

#button, #deletebutton, #button2, #buttonlogout{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}


#home {
  background-color: #CDD7DE;
  width: 100%;
  height: 100vh;
  float: right;
  
}

#currentappointments{
  margin-top: 60px;
  }


#invoices{

margin-right: 1000px;

}

#buttonlogout{
margin-top: 10px;
align: left;
margin-right: 282px;
}

#button2{

align: left;
margin-right: 100px;
}

#deletebutton{
align: right;
margin-left: 1100px;
}

tr.highlight {
  background-color: yellow;
}

#deletebutton:hover, #button2:hover, #buttonlogout:hover, #button:hover {
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}

.styled-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    min-width: 400px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin-left: 500px;
}

.styled-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: left;
}

.styled-table th,
.styled-table td {
    padding: 12px 15px;
}

.styled-table tbody tr {
    border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
    border-bottom: 2px solid #009879;
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
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "SignIn",
  data () {
    return {
      amountpaying: 0,
      amountpayed:0,
      amountowed:0,
      temp:'',
      inputDate: null,
      appointments: [],
      selectedAppointment: null,
    }
  },

  components: {
    AdminNavbar
  },

  created: function() {

  },

  methods: {

//actually delete account in database and then go to sign in
    deleteaccountpopup: function () {
       if (confirm('Do you want to Delete?')) {
           this.$router.push({name: "SignIn"}); //replace with delete account function and call this in that function
       } else {
           return false;
       }
    },
//removes current user
    logout: function () {
      this.$router.push({name: "SignIn"});
      
    },

    getAppointments: function() {
      AXIOS.get(`/appointments/` + this.inputDate, {}, {})
          .then((response) => {
            this.appointments = response.data
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
    },

    //This method converts date input field to a date object (Where we only care about the date)
    getDateClean(currDate) {

      console.log(currDate);

      this.inputDate = currDate;

      //console.log(currDate);

      //if (currDate == "") return "";
      //var selectedDate = new Date(currDate + "T00:00:00");

      this.getAppointments();

      //console.log(selectedDate);

        //return selectedDate;
    },

    selectRow(appointment) {
      this.selectedAppointment = appointment.id;
    },

    
    signIn: function (Username, Password) {
      AXIOS.get(`/signin/?username=` + Username + `&password=` + Password, {}, {})
          .then((response) => {
            this.Username= '',
            this.Password= '',
            this.errorMessage= '',
            this.$router.push({name: this.userType.concat("Home")});
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
           
    }
  }
}
  

  
</script>