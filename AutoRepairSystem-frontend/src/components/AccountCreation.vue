
<template>
<div id="signUp">
  <div id="central">
    <h2>Create Your Account</h2><br>
    <div id="fields">
      <div>
        Username:<br>
        <input class="creation-input" id="username-icon" type="text" v-model="username" placeholder="Username">
      </div>
      <div>
        Name:<br>
        <input class="creation-input" id="name-icon" type="text" v-model="name" placeholder="Name">
      </div>
      <div>
        Email:<br>
        <input class="creation-input" id="email-icon" type="text" v-model="email" placeholder="Email">
      </div>
      <div>
        Password:<br>
        <input class="creation-input" id="password-icon" type="password" v-model="password" placeholder="Password">
      </div>
      <div>
        Password:<br>
        <input class="creation-input" id="password-icon" type="password" v-model="passwordCheck" placeholder="Password">
      </div>
    </div>
    <div>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
      <br>
      <button id="signup-button" v-bind:disabled="!username || !password ||!passwordCheck || !name || !email" v-on:click="createAccount(username, password, passwordCheck, name, email)">Sign Up</button><br>
      <br>
      <span>I already have an account! <router-link to="/">Sign In</router-link></span>
    </div>
  </div>
</div>
</template>

<style>

#signUp {
  background-color: #CDD7DE;
  height: 100vh;
  position: relative;
}

#central {
  width: 400px;
  height: 500px;
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

#name-icon {
  background: white url(../assets/name-icon.png) left no-repeat;
}

#email-icon {
  background: white url(../assets/email-icon.png) left no-repeat;
}

#password-icon {
  background: white url(../assets/password-icon.png) left no-repeat;
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
            this.email = "";
            this.password = "";
            this.passwordCheck = "";
            this.username = "";
            this.errorMessage = "";
            this.name = "";

            AXIOS.get('/administrators/')
              .then((response) => {
                if(response.data.size() == 0) {
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