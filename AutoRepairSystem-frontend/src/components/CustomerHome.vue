<template>
<div>
<div class="navbarContainer">
  <CustomerNavbar/>
</div>

  <div id="home">
  
    <div id="currentappointments">
     <h2> Current Appointments </h2>
  <table class="content-table">
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
      </tbody>
    </table>
  </div>

  <div id="accountManagement">
    <div id="accountInfo">
      <h2>My Account: {{currentCustomer.username}}</h2>
      <table id="accountTable">
        <tr>
          <td id="leftBlock"><b>Name:</b><br>{{currentCustomer.name}}</td>
          <td id="rightBlock"><button class="btn-hover color small" style="display: inline" @click="openUpdateName()">Edit</button></td>
        </tr>
        <tr>
          <td id="leftBlock"><b>E-mail:</b><br>{{currentCustomer.email}}</td>
          <td id="rightBlock"><button class="btn-hover color small" @click="openUpdateEmail()">Edit</button></td>
        </tr>
      </table>
      <span v-if="updatingName">
        <input style="margin-top: 10px" type="text" v-model="newName" placeholder="New Name">
        <button class="btn-hover color small" @click="updateAccount(currentCustomer.username, currentCustomer.password, newName, currentCustomer.email)" v-bind:disabled="!newName">Update</button>
      </span>
      <span v-if="updatingEmail">
        <input style="margin-top: 10px" type="text" v-model="newEmail" placeholder="New E-mail">
        <button class="btn-hover color small" @click="updateAccount(currentCustomer.username, currentCustomer.password, currentCustomer.name, newEmail)" v-bind:disabled="!newEmail">Update</button>
      </span>
    </div>
    <div id="passwordChange">
      <h2>Change Password</h2>
      Current Password:<br><input type="password" v-model="password" placeholder="Password">
      <br><br>
      New Password:<br><input type="password" v-model="newPassword" placeholder="New Password">
      <br><br>
      <button @click="updatePassword()" class="btn-hover color" v-bind:disabled="!password || !newPassword">Update Password</button>
    </div>

    <div>
      <button id="deletebutton" class="btn-hover color" @click="deleteaccountpopup()">Delete Account</button>
      <br><br>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
    </div>
  </div>
</div>
</div>
</template>


<style>


* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

#deletebutton{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}


h2{
font-family: 'Poppins', sans-serif;
}

.btn-hover.color {
    background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
}

.small {
  height: 30px;
  width: 3px;
}

.btn-hover {
    width: 150px;
    font-size: 16px;
    font-weight: 600;
    color: #fff;
    cursor: pointer;
    margin: 20px;
    height: 40px;
    text-align:center;
    border: none;
    background-size: 300% 100%;

    border-radius: 20px;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

.btn-hover:hover {
    background-position: 100% 0;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}


#home {
  background-color: white;
  width: 100%;
  height: 100vh;
}

#currentappointments{
  margin-top: 60px;
  width: 50%;
  float: right;
  }

#accountInfo {
  margin-top: 60px;
  height: 260px;
}

#accountTable {
  width: 20%;
  margin: auto;
  border: 3px solid white;
  background-color: white;
}

#accountTable tr {
  border: 3px solid white;
  border-radius: 10px;
}

#leftBlock {
  float:left;
  text-align: left;
}

#rightBlock {
  float: right;
}

.content-table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  min-width: 400px;
  border-radius: 5px 5px 0 0;
  overflow: hidden;
  box-shadow: 0 0 20px white;

  padding-top: 100px;
  margin: auto;
  width: 30%;
}

.content-table thead tr {
  background-color: 
#013bbe;
  color: #ffffff;
  text-align: left;
  font-weight: bold;
}

.content-table th,
.content-table td {
  padding: 12px 15px;
}

.content-table tbody tr {
  border-bottom: 1px solid white;
}

.content-table tbody tr:nth-of-type(even) {
  background-color: white;
}

.content-table tbody tr:last-of-type {
  border-bottom: 2px solid #009879;
}

.content-table tbody tr.highlight {
  font-weight: bold;
  color: #009879;
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
  name: "CustomerHome",
  data () {
    return {
      appointments: [],
      username: this.$store.getters.getActiveUserName, //get actual username
      currentCustomer: "",
      updatingName: false,
      updatingEmail: false,
      newName: "",
      newEmail: "",
      password: "",
      newPassword: "",
      errorMessage: "",
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
        this.errorMessage = error
      });

      AXIOS.get("/customers/" + this.username)
        .then(response => {
          this.currentCustomer = response.data;
        })
        .catch(e => {
          this.errorMessage = e.response.data;
          console.log(e);
        })
    },
//actually delete account in database and then go to sign in
    deleteaccountpopup: function () {
       if (confirm("WARNING: Deleting an account is permanent! Are you certain you want to proceed?")) {
          console.log(this.deleteuser())
           
       } else {
           return false;
       }
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
    },

    openUpdateName: function() {
      this.updatingEmail = false;
      this.updatingName = !this.updatingName;
    },

    openUpdateEmail: function() {
      this.updatingName = false;
      this.updatingEmail = !this.updatingEmail;
    },

    updateAccount: function(username, password, name, email) {

      AXIOS.put("/endUsers/" + username + "?password=" + password + "&name=" + name + "&email=" + email)
        .then(response => {
          this.currentCustomer = response.data;
          this.updatingEmail = false;
          this.updatingName = false;
          this.newName = "";
          this.newEmail = "";
          this.password = "";
          this.newPassword = "";
          this.errorMessage = "";
        })
        .catch(e => {
          this.errorMessage = e.response.data;
          console.log(e);
        });

    },

    updatePassword: function() {
      if (this.password !== this.currentCustomer.password) {
      console.log("yeet");
        this.errorMessage = "Entered password does not match current password!";
        return;
      }

      var confirmation = window.confirm("Are you sure you want to change your password?");

      if (confirmation) {
        this.updateAccount(this.currentCustomer.username, this.newPassword, this.currentCustomer.name, this.currentCustomer.email);
      }
    }
  }
}
  

  

</script>