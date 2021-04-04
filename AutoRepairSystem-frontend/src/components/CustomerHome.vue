<template>
<div>
  <div class="navbarContainer">
    <CustomerNavbar/>
  </div>

  <div id="home">
  
    <div id="currentappointments">
     <h2> Current Appointments </h2>
  <table class="styled-table">
    <thead>
        <tr>
            <th>Services</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
    </thead>
      <tbody>
          <tr v-for="appointment in appointments">
            <td>{{ formattedServices(appointment) }}</td>
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
    margin-left: auto;
    margin-right: auto;
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
import CustomerNavbar from '@/components/CustomerNavbar'

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
      amountpaying: 0,
      amountpayed:0,
      amountowed:0,
      temp:'',
      appointments: [],
      username: this.$store.getters.getActiveUserName, //get actual username
  
    }
  },

   components: {
    CustomerNavbar
  },
  
  created: function() {
    this.fetch()
  },

  methods: {
    fetch (){
			AXIOS.post(`/appointments/bycustomer`, {username: this.username}, {})
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


    checkinvoices: function () {
      this.$router.push({name: "PayInvoice"});
      
    },

//removes current user
    logout: function () {
      this.$router.push({name: "SignIn"});
      
    },

    browseappointment: function () {
      this.$router.push({name: "AvailableAppointments"});
      
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

    formattedServices: function(appointment) {

      var servicesString = "";

      var i=0;
      for (; i<appointment.services.length-1; i++) {
          servicesString += appointment.services[i].name + ", ";
      }
      servicesString += appointment.services[i].name;

      return servicesString;
    }
  }
}
  

  

</script>