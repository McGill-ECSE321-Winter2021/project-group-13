
<template>
  <div id="LogIn">
    <table id="logintable">
      <br>
      <br>
      <br>
      <h3> Sign In </h3>
  <label for="UserType">Select User Type:</label>
<select name="UserType" id="usertypedropdown">
  <option @click="setCustomer">Customer</option>
  <option @click="setTechnician" >Technician</option>
  <option @click="setAdministrator">Administrator</option>
</select>
      <br>
      <br>
      <tr>
          <td>
              <input type="text" v-model="Username" placeholder="username">
          </td>
      </tr>
       <tr>
        <td>
         <input type="password" v-model="Password" placeholder="password">
        </td>
      </tr>
      <tr>
        <td>
          <br>
        <button v-bind:disabled="!Username || !Password" @click="signIn(Username, Password)" > LogIn</button>
        </td>
      </tr>
</table>
<p>
      <br>
      <span v-if="errorMessage" style="color:red">Error: {{errorMessage}}</span>
    </p>  
<table id="createaccounttable">
      <br>
      <h3> Create Account </h3>
      <br>
      <tr>
          <td>
              <input type="text" v-model="name" placeholder="name">
          </td>
      </tr>
      <tr>
        <td>
         <input type="text" v-model="email" placeholder="email">
        </td>
      </tr>
      <tr>
        <td>
         <input type="text" v-model="username2" placeholder="username">
        </td>
      </tr>
      <tr>
        <td>
         <input type="password" v-model="password2" placeholder="password">
        </td>
      </tr>
      <tr>
        <td>
          <br>
        <button v-bind:disabled="!username2 || !password2 || !name || !email" @click="createAccount(username2, password2, name, email)" >Create Account</button>
        </td>
      </tr>
    </table>
    <p>
      <br>
      <span v-if="error2" style="color:red">Error: {{error2}}</span>
    </p>
    </div>
</template>

<style>

#LogIn {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
    position: relative;
  }

#logintable{
margin-top: 10px;
margin-left: 580px;
  }

#createaccounttable{
margin-top: 10px;
margin-left: 600px;

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
  name: "SignIn",
  data () {
    return {
      Username: '',
      Password: '',
      errorMessage: '',
      userType: '',
      username2: '',
      password2: '',
      name: '',
      email: '',
      error2: ''
    }
  },

  methods: {
    
    signIn: function (Username, Password) {
      AXIOS.get('/signin', '?username=' + Username  + '?password=' +  Password)
          .then((response) => {
            this.Username= '',
            this.Password= '',
            this.errorMessage= '',
            this.$router.push({ name: "CustomerHome" });
          })
          
          .catch((e) => {
            var error = "Error Has Occured";
            console.log(e);
            this.errorMessage = error;
          });

          
      },
  
    createAccount: function (username2, password2, name, email) {
      AXIOS({
          method: "post",
          url: "/customers".concat(this.username2),
          params: {
            name: this.name,
            email: this.email,
            password: this.password2,
          },
        })
          .then((response) => {
            this.email = "";
            this.password2 = "";
            this.username2 = "";
            this.error = "";
            this.name = "";
            this.$router.push({ name: "CustomerHome" });
          })
          .catch((e) => {
            var errorMsg = e.message;
            console.log(e);
            this.error = errorMsg;
          });

      
    },
    setCustomer: function () {
      this.userType = "customer";
    },
    setTechnician: function () {
      this.userType = "technician";
    },
    setAdministrator: function () {
      this.userType = "administrator";
    }

  },
}

</script>