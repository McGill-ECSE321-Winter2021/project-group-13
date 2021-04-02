<template>

<html>

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<div id="LogIn">
  <div id="central-signin">
    <h2>Sign In</h2>
    <div id="fields-signin">
      
      <div>
		<i class="fa fa-user icon"></i>
        <input class="pass2" id="username-icon" type="text" v-model="username" placeholder="Username">
      </div>

      <div>
		<i class="fa fa-lock icon"></i>
        <input class="pass2" id="password-icon" type="password" v-model="password" placeholder="Password">
      </div>
    </div>
    
    <div>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
      <br>  
      <button id="button" class="btn-hover color" v-bind:disabled="!username || !password || !userType" @click="isValidType(userType, username, password)"> Log In </button>
      <br><br>
      <span>Create an account? <router-link to="/AccountCreation">Sign up</router-link></span>
    </div>
  </div>
</div>

</html>

</template>

<style>

* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.btn-hover.color {
    background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
}

.btn-hover {
    width: 90px;
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

h2{
font-family: 'Poppins', sans-serif;
}

.btn-hover:focus {
    outline: none;
}

#button-signin {
  margin-top: 15px;
}

#LogIn {
  height: 100vh;
  position: relative;
  
  /* https://wallpaperaccess.com/full/1900672.jpg */
  
  background-image: url("https://i.ibb.co/bX833PJ/1900672.png");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

#central-signin {
  width: 400px;
  height: 500px;
  position: absolute;
  top: 50%;
  background-color: white;
  left: 50%;
  margin: -42vh 0 0 -200px;
  padding-top: 100px;
  
    box-shadow: inset 0 -3em 3em rgba(0,0,0,0), 
		 0.3em 0.3em 1em rgba(0,0,0,0.2);

    border-radius: 10px; 
    overflow: hidden;
}

.pass2{
	border-top-width: 0;
	border-left-width: 0;
	border-right-width: 0;
	border-bottom-width: 3px;
	margin-top: 10px;
	border-color: blue;
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
          this.$store.dispatch("setActiveUserName", this.username);
          this.username= '',
          this.password= '',
          this.errorMessage= '';
          if (response.data.userType === "customer") {
            this.$router.push({name: "CustomerHome" });
          } else if(response.data.userType === "technician") {
            this.$router.push({name: "TechnicianHome"});
          } else if (response.data.userType === "administrator") {
            this.$router.push({name: "AdministratorHome"}) 
          }
        })
        .catch((e) => {
          var error= e.response.data;
          this.errorMessage = error;
        });
           
    },
      
  },
  
}
</script>