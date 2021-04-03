<template>
<div>
  <div id="home">
    <h2>Home (TECHNICIAN)</h2>
    <h4>My Info : {{username}}</h4>
  
<div id="invoices">
       <button id="buttonlogout" @click="logout()">Log Out </button> 
       </div>
    <div id="currentappointments">
     <h2> Scheduled Appointments </h2>
  <table class="styled-table">
    <thead>
        <tr>
            <th>Service</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
    </thead>
      <tbody>
          <tr v-for="appointment in appointments">
            <td>{{ appointment.services.name }}</td>
            <td>{{ appointment.date }}</td>
            <td>{{ appointment.startTime }}</td>
            <td>{{ appointment.endTime }}</td>
          </tr>
          <tr v-for="n in emptyAdministrators" v-bind:key="n">
          <td></td>
          <td></td>
          <td></td>
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

 #deletebutton, #buttonlogout{
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


#deletebutton{
align: right;
margin-left: 1100px;
}

#deletebutton:hover, #buttonlogout:hover {
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}

.styled-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    min-width: 400px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin-left: auto;
    margin-right: auto;
}

.styled-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: center;
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


</style>

<script>

import axios from "axios";
var config = require("../../config");
var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;


  var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "SignIn",
  data () {
    return {
      appointments: [],
      username: this.$store.getters.getActiveUserName, //get actual username
    }
  },

  components: {
  },

  created: function() {
    this.fetch()
  },

  methods: {

    fetch (){
			AXIOS.post(`/appointments/bytechnician`, {username: this.username}, {})
      .then(response => {
        this.appointments = response.data
      })
      .catch(e => {
        var error = e.response.data
        console.log(error)
        this.endUserError = error
      })
    },

//actually delete account in database and then go to sign in
    deleteaccountpopup: function () {
       if (confirm('Do you want to Delete?')) {
          console.log(this.deleteuser())
           
       } else {
           return false;
       }
    },


    deleteuser: function () {
      AXIOS.delete(`/endUsers/`.concat(this.username), {}, {})
          .then((response) => {
            this.$router.push({name: "SignIn"});
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
           
    },


//removes current user
    logout: function () {
      this.$router.push({name: "SignIn"});
      
    }

  }
}
  

  
</script>