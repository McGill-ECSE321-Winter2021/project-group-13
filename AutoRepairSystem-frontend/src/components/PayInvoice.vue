<template>
<div>

  <div class="navbarContainer">
    <CustomerNavbar/>
  </div>

  <div id="payment">
    <h2>Payment</h2>
     <div id="amountowed">
       <button id="button2" @click="getamountowed()">View Amount Owed</button>
       <span v-if=" temp2=='5'" style="color:blue">No Outstanding Balance, Please Return Home!</span> <br>
       <span v-if=" temp=='3' && amountowed==0" style="color:blue">No Outstanding Balance, Please Return Home!</span> <br>
   <h2 v-if="amountowed && amountowed!=0"> Amount Owed: ${{amountowed}} </h2>
   <br>
   <span v-if="amountowed!=0 && amountpaying && amountpaying<=amountowed && amountpaying>0" style="color:red">New Amount Owed If You Pay ${{amountpaying}}:<br> ${{amountowed}} - ${{amountpaying}} = ${{amountowed - amountpaying}}</span>
   </div>
   <div id="homebutton">
     <button id="button" @click="returnhome()">Back To Home</button>
     </div>
    <div id="textbar">
     <span v-if="!amountowed && amountpayed==amountowed && temp!=3" style="color:red"> Note: User Must View Amount Owed Before They Are Able To Pay. </span>
      <br>
      <br>
      <span v-if="amountpaying > amountowed" style="color:red">You Cannot Pay More Than You Owe!</span> <br>
 <label for="appt-time">Enter amount to pay: $ </label><span v-if="temp6=='2'"  id="amounttopay"><input type="number" step="1" min="1"  v-model="amountpaying" placeholder="Amount To Pay" id="amnt" name="amnt"></span>
 <br>
 <br>
 <button id="button" v-bind:disabled="!amountpaying || !amountowed || amountowed==0 || amountpaying>amountowed || amountpaying<=0 " @click="paynow(amountpaying)">Pay Now</button> <br>
 <br>
 <span v-if="amountpayed" style="color:blue">Thank You For Your Payment Of  ${{amountpayed}}</span> <br>
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



#button2.disabled{
  border-color: #3498aa;
  color: #aaa;
}


#payment {
  background-color: #CDD7DE;
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
      amountpaying: 0,
      amountpayed:0,
      amountowed:0,
      temp:'',
      temp2:'',
      temp6:'',
      Username: 'test', //actually get username of current user
    }
  },

  components: {
    CustomerNavbar
  },

  methods: {

    returnhome: function () {
      
      this.$router.push({name: "CustomerHome"});
    },

    getamountowed: function () {
      this.temp6='2';
      this.amountowed=2; //actually get amount that user owes
      if(!this.amountowed){
        this.temp2='5';
      }
      var elem = document.getElementById('button2');
      elem.parentNode.removeChild(elem);
      this.temp='3';
     return false;
      
    },

    paynow: function () {
      
      this.amountpayed=this.amountpaying;
      this.amountowed=this.amountowed - this.amountpayed;
      //actually update database/what user owes
      //this.amountowed=console.log(this.payment())
      if(this.anountowed==0){
        this.temp2='5';
      }
      this.amountpaying=0;
      
    },
    
    payment: function (amountPaying) {
      AXIOS.put(`/customers/`.concat(this.Username).concat("/payment" + `&amount=` + this.amountpaying), {}, {})
          .then((response) => {
           
          })
          
          .catch((e) => {
            var error= e.response.data;
            this.errorMessage = error;
          });
           
    }
  }
}
  

  

</script>