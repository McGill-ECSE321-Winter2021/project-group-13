
<template>

<html>

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<img class="logo" src="https://i.ibb.co/kcj0HD4/circle-cropped-1.png" width="100">

<div id="signUp">
  <div id="central">
    <h2>Create Your Account</h2><br>
    <div id="fields">
      <div>
        <input class="textbox" type="text" v-model="username" placeholder="Username">
      </div>
      <div>
        <input class="textbox" type="text" v-model="name" placeholder="Name">
      </div>
      <div>
        <input class="textbox" type="text" v-model="email" placeholder="Email">
      </div>
      <div>
        <input class="textbox" type="password" v-model="password" placeholder="Password">
      </div>
      <div>
        <input class="textbox" type="password" v-model="passwordCheck" placeholder="Confirm Password">
      </div>
    </div>
    <div>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
      <br>
      <button id="signup-button" class="btn-hover color" v-bind:disabled="!username || !password ||!passwordCheck || !name || !email" v-on:click="createAccount(username, password, passwordCheck, name, email)">Sign Up</button><br>
      <br>
      <span>I already have an account! <router-link to="/">Sign In</router-link></span>
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

.textbox{
	border-top-width: 0;
	border-left-width: 0;
	border-right-width: 0;
	border-bottom-width: 3px;
	margin-top: 10px;
	border-color: blue;
}

#signUp {
  background-color: #CDD7DE;
  height: 100vh;
  position: relative;

  background-image: url("https://i.ibb.co/P9mSh5r/Los-Santos-Customs-GTAV-Burton.png");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

#central {
  width: 400px;
  height: 500px;
  position: absolute;
  top: 50%;
  background-color: white;
  left: 50%;
  margin: -42vh 0 0 -200px;
  padding-top: 30px;
  
    box-shadow: inset 0 -3em 3em rgba(0,0,0,0), 
		 0.3em 0.3em 1em rgba(0,0,0,0.2);

    border-radius: 10px; 
    overflow: hidden;
}

input.creation-input {
  padding-left: 25px;
  border-radius: 10px;
}

input.creation-input:focus {
  border: 2px solid #555;
  -webkit-transition: 0.5s;
}

#fields {
  text-align: left;
}

#fields div {
  margin-left: 100px;
  margin-bottom: 5px;
}

#signup-button {
  width: 200px;
  background-color: #3498db;
}

body {
  overflow: hidden;
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
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "AccountCreation",
  data () {
    return {
      username: "",
      password: "",
      passwordCheck: "",
      email: "",
      name: "",
      errorMessage: "",
    }
  },

  methods: {
    createAccount: function (username, password, passwordCheck, name, email) {

      if (password !== passwordCheck) {
          this.errorMessage = "Both passwords must match!";
          return;
      }

      AXIOS.post(`/customers/`.concat(username) + `?password=` + password + `&name=` + name + `&email=` + email, {}, {})
          .then((response) => {
            this.$store.dispatch("setActiveUserName", this.username);
            this.email = "";
            this.password = "";
            this.passwordCheck = "";
            this.username = "";
            this.errorMessage = "";
            this.name = "";

            AXIOS.get('/administrators/')
              .then((response) => {
                if(response.data.length == 0) {
                  AXIOS.post('/make/'.concat(username) + '/administrator') 
                  this.$router.push({ name: "AdministratorHome" });
                }
                else {
                  this.$router.push({ name: "CustomerHome" });
                }
              });
            
          })
          .catch((e) => {
            var errormsg = e.response.data;
            this.errorMessage = errormsg;
          });
    }
  },
}

</script>