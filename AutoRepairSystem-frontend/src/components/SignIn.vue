
<template>
<div id="LogIn">
  <div id="central-signin">
    <h2>Account Sign In</h2>
    <div id="fields-signin">
      <div>
        <select type="text" id="select"  v-model="userType">
          <option value="" disabled selected>Select User Type</option>
          <option @onchange="setCustomer()">Customer</option>
          <option @onchange="setTechnician()" >Technician</option>
          <option @onchange="setAdministrator()">Administrator</option>
        </select>
      </div>
      
      <div>
        <input class="pass2" id="username-icon" type="text" v-model="username" placeholder="Username">
      </div>

      <div>
        <input class="pass2" id="password-icon" type="password" v-model="password" placeholder="Password">
      </div>
    </div>
    
    <div>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
      <br>  
      <button id="button" v-bind:disabled="!username || !password || !userType" @click="isValidType(userType, username, password)"> Log In </button>
      <br><br>
      <span>Create an account? <router-link to="/AccountCreation">Sign up</router-link></span>
    </div>
  </div>
</div>
</template>

<style>

#button:enabled{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}
#button:enabled:hover {
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}

#button {
  width: 200px;
}

#button-signin {
  margin-top: 15px;
}

#select{
 border-radius:15px;
 background-color:darkgrey;
 margin-top: 25px;
}

input.pass2 {
  margin-top: 10px;
  padding-left: 25px;
  border-radius: 10px;
}

input.pass2:focus {
  border: 2px solid #555;
  -webkit-transition: 0.5s;
}

#LogIn {
  background-color: #CDD7DE;
  height: 100vh;
  position: relative;
}

#central-signin {
  width: 400px;
  height: 320px;
  background-color: #EAF0F4;
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -42vh 0 0 -200px;
  padding-top: 20px;
}

#username-icon {
  background: white url(../assets/username-icon.png) left no-repeat;
}

#password-icon {
  background: white url(../assets/password-icon.png) left no-repeat;
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
      username: '',
      password: '',
      errorMessage: '',
      userType: '',
    }
  },

  methods: {

    
    signIn: function (username, password) {
      AXIOS.get(`/signin/?username=` + username + `&password=` + password, {}, {})
          .then((response) => {
            this.username= '',
            this.password= '',
            this.errorMessage= '',
            this.$router.push({name: this.userType.concat("Home")});
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
           
      },

       isValidType: function (userType, username, password) {
          var usertype="";
          if(userType == "Customer") usertype="endUsers";
          if(userType == "Technician") usertype="technicians";
          if(userType == "Administrator") usertype="administrators";
      AXIOS.get('/'.concat(usertype).concat("/").concat(username), {}, {})
          .then((response) => {
            console.log(this.signIn(username, password))
            
          })
          .catch((e) => {
            var errorMsg = e.response.data;
            this.errorMessage = errorMsg;
          });
    },
      
    },
    setCustomer: function () {
      
      this.userType = "Customer";
    },
    setTechnician: function () {
    
      this.userType = "Technician";
    },
    setAdministrator: function () {
      
      this.userType = "Administrator";
    }

  
}

</script>