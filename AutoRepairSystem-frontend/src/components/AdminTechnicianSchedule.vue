<template>
	<div>
    <div>
      <AdminNavbar/>
    </div>
    <div class="side-menu">
      <div id="business-hour-menu">
        <h2>Manage <br>Business Hours</h2>
        <div v-if="selectedBusinessHourId <= 0 || selectedBusinessHourId == null">
          <div class = "time-pick">
          <p class = "left-pick"> Open Time:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startBusinessHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Close Time:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endBusinessHourInput"></vue-timepicker>
          </p>
          </div>
          <div class ="button-container">
            <button
            v-bind:disabled="(selectedBusinessHour == null || businessHourStart.HH == '' || businessHourStart.mm == '' ||  businessHourEnd.HH == '' || businessHourEnd.mm == '')" 
            v-on:click="createBusinessHour(selectedBusinessHour.col)" 
            >
              Create Business Hour
            </button>
          </div>
        </div>
          <div v-if="selectedBusinessHourId > 0">
            <div class = "time-pick">
            <p class = "left-pick"> New Open Time:</p> 
            <p class = "right-pick" > 
              <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startBusinessHourInput"></vue-timepicker>
            </p>
            </div>
            <div class = "time-pick">
            <p class = "left-pick"> New Close Time:</p> 
            <p class = "right-pick" > 
              <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endBusinessHourInput"></vue-timepicker>
            </p>
            </div>
            <div>
            <div class = "button-container" v-if="selectedBusinessHourId > 0" >
              <button style ="margin-bottom: 10px"
              v-bind:disabled="(selectedBusinessHour == null || businessHourStart.HH == '' || businessHourStart.mm == '' ||  businessHourEnd.HH == '' || businessHourEnd.mm == '')" 
              v-on:click="updateBusinessHour(selectedBusinessHour)" 
              >
                Update Business Hour
              </button>
              <button
              v-bind:disabled="(selectedBusinessHour == null)"
              v-on:click="deleteBusinessHour(selectedBusinessHour)"
              >
                Delete Business Hour
              </button>
            </div>
          </div>
        </div>
      </div> <!-- close business hour window -->
      <div class="technicianHourWindow">
        <h2>Manage <br> Technician Shifts</h2>
        <div class = "createTechnicianHour" v-if="selectedTechnicianHourId <= 0 || selectedTechnicianHourId == null">
          <div class = "time-pick">
          <p class = "left-pick"> Shift Start :</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Shift End:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Lunch Break: </p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="breakTimeInput"></vue-timepicker>
          </p>
          </div>
          <div>
            <button
            v-bind:disabled="(selectedTechnicianHour == null || technicianHourStart.HH == '' || technicianHourStart.mm == '' ||  technicianHourEnd.HH == '' || technicianHourEnd.mm == '')" 
            v-on:click="createTechnicianHour(selectedTechnicianHour.col,selectedTechnician)" 
            >
              Schedule Shift
            </button>
          </div>
        </div>
          <div class = "updateTechnicianHour" v-if="selectedTechnicianHourId > 0">
          <div class = "time-pick">
          <p class = "left-pick"> Updated Shift Start :</p> 
          <p class = "right-pick"> 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="startTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Updated Shift End:</p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="endTechnicianHourInput"></vue-timepicker>
          </p>
          </div>
          <div class = "time-pick">
          <p class = "left-pick"> Lunch Break: </p> 
          <p class = "right-pick" > 
            <vue-timepicker input-width = "100px" format="HH:mm" :minute-interval="15" @input="breakTimeInput"></vue-timepicker>
          </p>
          </div>
          <div>
            <div v-if="selectedTechnicianHourId > 0" >
              <button
              v-bind:disabled="(selectedTechnicianHour == null || technicianHourStart.HH == '' || technicianHourStart.mm == '' ||  technicianHourEnd.HH == '' || technicianHourEnd.mm == '')" 
              v-on:click="updateTechnicianHour(selectedTechnicianHour)" 
              >
                Update Shift
              </button>
              <button
              v-bind:disabled="(selectedTechnicianHour == null)"
              v-on:click="deleteTechnicianHour(selectedTechnicianHour)"
              >
                Delete Shift
              </button>
            </div>
          </div>
        </div>
      </div> <!-- close technician hour window -->
    </div> <!-- close sidebar -->
		<div class="calendar">
    <h2 style="font-size: 35px"> Technician Schedule</h2>
      <div id="month-header"><span id="month">{{month}} {{year}}</span></div>
      <div id="week-buttons"> 
        <button class="week-change" v-on:click="changeWeek(false)">
          Previous
        </button>
        <button class="week-change" v-on:click="changeWeek(true)">
          Next  
        </button>
      </div>
      <table id="hours-table">
        <tr id="calendar-top-row">
          <td></td>
          <td>Sun.<br>{{weekdays[0]}}</td>
          <td>Mon.<br>{{weekdays[1]}}</td>
          <td>Tue.<br>{{weekdays[2]}}</td>
          <td>Wed.<br>{{weekdays[3]}}</td>
          <td>Thu.<br>{{weekdays[4]}}</td>
          <td>Fri.<br>{{weekdays[5]}}</td>
          <td>Sat.<br>{{weekdays[6]}}</td>
        </tr>
        <tr id="business-hour-row">  
          <td class ="row-header"> Business Hours </td>
          <td class="hour-slot" 
          v-for="businessHour in businessHoursWeek" v-bind:key="businessHour.id" 
          @click="selectBusinessHour(businessHour)" 
          :class="{'highlight': (businessHour.id == selectedBusinessHourId)}" >
            <div v-if="businessHour.id > 0" >
            open: {{ formatTimeDisplay(businessHour.startTime) }}<br> 
            close: {{ formatTimeDisplay(businessHour.endTime) }}
            </div>
            <div v-if="businessHour.id < 0">
              CLOSED
            </div>
          </td>
          
        </tr>
        <tr style = "height: 10px;"></tr>
        <tr class="technician-row" v-for="(technician,index) in technicians" v-bind:key="technician.username">
          <td style="font-weight: bolder"> {{technician.name}} </td>
          <td class="hour-slot" 
          v-for="technicianHour in technicianHoursWeek[index]" v-bind:key="technicianHour.id" 
          @click="selectTechnicianHour(technicianHour), selectTechnician(technician)" 
          :class="{'highlight': (technicianHour.id == selectedTechnicianHourId)}" >
            <div >
              <div v-if="technicianHour.id > 0">
                {{ formatTimeDisplay(technicianHour.startTime) }} <span>&ndash;</span> {{ formatTimeDisplay(technicianHour.endTime) }}
              </div>
              <div v-if="technicianHour.id < 0">
                X
              </div>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<style>

#business-hour-row {
  height: 60px;
  border: 1px solid black;
}

#business-hour-menu {
  height: 33%;
}

.button-container {
  height: 80px;
}

.row-header {
  text-align: center;
  font-weight: bolder;
}

.time-pick {
  height: 60px;
}

.left-pick {
  position:relative; top:5px;
  float: left;
  margin-left: 5px;
}

.right-pick {
  float: right;
  margin-right: 5px;
}

.hour-slot {
  border: 1px solid black;
  empty-cells: show;
}

.technician-row {
  height: 60px;
  border: 1px solid black;
}

.technician-row:nth-of-type(odd) {
  background-color: #f3f3f3;
}

.side-menu {
  background-color: black;
  width: 300px;
  height: 100vh;
  float: left;
  padding: 10px;
  color: white;
}

.calendar {
  background-color: white;
  width: calc(100% - 300px);
  height: 100vh;
  float: right;
  padding: 15px;
}

#header-appointments {
  background: white;
}

.back {
  position: absolute;
    bottom: 10px;
    left: 0%;
    margin-left: 10px;
}

#month-header {
  font-size: 30px;
  float: left;
  margin-left: 15px;
}

#week-buttons {
  float: right;
  margin-bottom: 5px;
}

.highlight {
 background: yellow;
}

button.week-change {
  margin-left: 5px;
  margin-right: 5px;
  width: 100px;
  font-size: 20px;
}

#hours-table {
  width: 100%;
  table-layout: fixed;
}

#calendar-top-row, #calendar-top-row td {
  border: 1px solid black;
  text-align: center;
}

#calendar-top-row {
  background-color: #013bbe;
  color: white;
  font-size: 20px;
  height: 80px;
}

body {
  overflow: hidden; /*This stops the page from having a scroll bar (might be able to remove this later)*/
}

#app {
  margin-top: 0;
}

table {
  empty-cells: show;
}
</style>

<script src="./AdminTechnicianSchedule.js">
</script>