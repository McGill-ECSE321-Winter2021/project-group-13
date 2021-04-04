<template>
<div>

  <div class="navbarContainer">
    <CustomerNavbar/>
  </div>

  <div id="payment">
      <div id="centr">
    <h2 style="margin-bottom: 70px;">Payment</h2>

    <h4 style="display: inline;">$</h4><h1 style="display: inline">{{amountOwed}}</h1>
    <h4>balance</h4>

    <div id="textbar">
 <label for="appt-time">Enter amount to pay: $ </label>
 <input type="number" step="1" min="1"  v-model="amountPaying" placeholder="Amount To Pay" id="amnt" name="amnt">

 <br>
 <br>
 <button id="button" class="btn-hover color" v-bind:disabled="!amountPaying || amountPaying>amountOwed || amountPaying<=0" @click="addPayment(amountPaying)">Pay Now</button> <br>
 <br>
 </div>
 </div>
 </div>
</div>
</template>
<style>

#LogIn {
  height: 100vh;
  position: relative;
  
  /* https://wallpaperaccess.com/full/1900672.jpg */
  
  background-image: url("https://i.ibb.co/bX833PJ/1900672.png");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

#centr {
  width: 400px;
  height: 500px;
  position: absolute;
  top: 60%;
  background-color: white;
  left: 50%;
  margin: -42vh 0 0 -200px;
  padding-top: 40px;
  
    box-shadow: inset 0 -3em 3em rgba(0,0,0,0), 
		 0.3em 0.3em 1em rgba(0,0,0,0.2);

    border-radius: 10px; 
    overflow: hidden;
}

h2{
font-family: 'Poppins', sans-serif;
}

#button:enabled{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}

.btn-hover.color {
    background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
}


.btn-hover.color2 {
    background-image: linear-gradient(to right, #DC1C13, #EA4C46, #F07470);
}

.btn-hover:disabled{
  background-color: gray;
}

.btn-hover {
    width: 150px;
    font-size: 16px;
    font-weight: 600;
    color: #fff;
    cursor: pointer;
    margin: 5px;
    height: 40px;
    text-align:center;
    border: none;
    background-size: 300% 100%;

    border-radius: 20px;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;

    margin-top: 20px;
}

.small {
  width: 100px;
}

.btn-hover:hover {
    background-position: 100% 0;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

#button2.disabled{
  border-color: #3498aa;
  color: #aaa;
}


#payment {
  background-color: white;
  width: 100%;
  height: 100vh;
  float: right;
  
}

#textbar{
  margin-top: 100px;
}

#button2{
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  -webkit-transition: all 150ms ease-in-out;
  transition: all 150ms ease-in-out;
}

#amountowed{

margin-right: 1000px;

}

#homebutton{
  margin-top: -30px;
align: right;
margin-left: 1000px;
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
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "SignIn",
  data () {
    return {
      amountPaying: 0,
      amountOwed: 0,
      Username: this.$store.getters.getActiveUserName,
    }
  },

  components: {
    CustomerNavbar
  },

  created: function() {
    this.getAmountOwed()
    window.setInterval(() => {
      this.getAmountOwed()
    }, 2000)
  },

  methods: {

    returnhome: function () {
      
      this.$router.push({name: "CustomerHome"});
    },

    getAmountOwed: function () {
      AXIOS.get(`/customers/`.concat(this.Username), {}, {})
          .then((response) => {
           this.amountOwed=response.data.amountOwed;
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
    },
    
    addPayment: function (amount) {
      AXIOS.put(`/customers/`.concat(this.Username).concat("/payment" + `?amount=` + amount), {}, {})
          .then((response) => {
            this.getAmountOwed();
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
            window.alert(this.errorMessage);
          });
           
    }
  }
}
  

  

</script>