
<template>
  <div id="LogIn">
    <table id="logintable">
      <br>
      <br>
      <br>
      <h3> Sign In </h3>
<select type="text" id="select"  v-model="userType">
  <option value="" disabled selected>Select User Type</option>
  <option @onchange="setCustomer()">Customer</option>
  <option @onchange="setTechnician()" >Technician</option>
  <option @onchange="setAdministrator()">Administrator</option>
</select>
      <br>
      <br>
      <tr>
          <td>
              <input id="pass2" type="text" v-model="Username" placeholder="username">
          </td>
      </tr>
       <tr>
        <td>
         <input id="pass2" type="password" v-model="Password" placeholder="password">
        </td>
      </tr>
      <tr>
        <td>
          <br>
        <button id="button" v-bind:disabled="!Username || !Password || !userType" @click="isValidType(userType, Username, Password)"> Log In </button>
        
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
              <input id="pass2" type="text" v-model="name" placeholder="name">
          </td>
      </tr>
      <tr>
        <td>
         <input id="pass2" type="text" v-model="email" placeholder="email">
        </td>
      </tr>
      <tr>
        <td>
         <input id="pass2" type="text" v-model="username2" placeholder="username">
        </td>
      </tr>
      <tr>
        <td>
         <input id="pass2" type="password" v-model="password2" placeholder="password">
        </td>
      </tr>
      <tr>
        <td>
          <br>
        <button id="button" v-bind:disabled="!username2 || !password2 || !name || !email" @click="createAccount(username2, password2, name, email)" >Create Account</button>
        </td>
      </tr>
    </table>
    <p>
      <br>
      <span v-if="error" style="color:red">Error: {{error}}</span>
    </p>
    </div>
</template>

<style>

#LogIn {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #CDD7DE;
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

  #pass2{
  padding:5px;
  border-radius:10px;
  width:100%
  }


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

#select{
 border-radius:15px;
 background-color:darkgrey;
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
      error: '',
    
    }
  },

  methods: {

    
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
           
      },

       isValidType: function (userType, Username, Password) {
          var usertype="";
          if(userType == "Customer") usertype="endusers";
          if(userType == "Technician") usertype="technicians";
          if(userType == "Administrator") usertype="administrators";
      AXIOS.get('/'.concat(usertype).concat("/").concat(Username), {}, {})
          .then((response) => {
            console.log(this.signIn(Username, Password))
            
          })
          .catch((e) => {
            var errorMsg = e.response.data;
            this.errorMessage = errorMsg;
          });
    },
  
    createAccount: function (username2, password2, name, email) {
      AXIOS.post(`/customers/`.concat(username2) + `?password=` + password2 + `&name=` + name + `&email=` + email, {}, {})
          .then((response) => {
            this.email = "";
            this.password2 = "";
            this.username2 = "";
            this.error = "";
            this.name = "";
            this.$router.push({ name: "CustomerHome" });
          })
          .catch((e) => {
            var errormsg = e.response.data;
            this.errorMessage = errormsg;
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