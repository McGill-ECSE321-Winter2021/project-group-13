<template>
<div id="appointments-page">

  <div class="sidemenu">
    <h2>Book Appointment</h2>
    <div id="service-block">
      <h3>Services</h3>
      Select Desired Services:
      <div class="service-table">
      <table id="service-table">
        <thead>
        <tr>
          <th>Service</th>
          <th>Duration</th>
          <th>Price</th>
        </tr>
        </thead>
        <tbody>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service1" name="service1" value="Service1"><label for="service1"><span id="service">Service 1</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service2" name="service2" value="Service2"><label for="service2"><span id="service">Service 2</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service3" name="service3" value="Service3"><label for="service3"><span id="service">Service 3</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service4" name="service4" value="Service4"><label for="service4"><span id="service">Service 4</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service5" name="service5" value="Service5"><label for="service5"><span id="service">Service 5</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service6" name="service6" value="Service6"><label for="service6"><span id="service">Service 6</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service6" name="service6" value="Service6"><label for="service6"><span id="service">Service 6</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service6" name="service6" value="Service6"><label for="service6"><span id="service">Service 6</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service6" name="service6" value="Service6"><label for="service6"><span id="service">Service 6</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
          <tr>
            <td><div id="servicediv"><input type="checkbox" id="service6" name="service6" value="Service6"><label for="service6"><span id="service">Service 6</span></label></div></td>
            <td>10:00</td>
            <td>$37</td>
          </tr>
        </tbody>
      </table>
      </div>

      <div class="datetime-block">
        <h3>Date & Time</h3>
        <br>
        <label for="appt-date">Select a Date: </label><span id="datetimepicker"><input type="date" id="appt-date" name="appt-date"></span>
        <br><br>
        <label for="appt-time">Select a time: </label><span id="datetimepicker"><input type="time" id="appt-time" name="appt-time"></span>
      </div>

      <div style="margin-top: 30px;">
        <button>Book Appointment</button>
      </div>

      <button class="back">Back</button>

    </div>
  </div>

  <div class="calendar">
    <h2>Availability Calendar</h2>
    <div id="month-header"><button><</button><span id="month">March 2021</span><button>></button></div>
    <table id="appointments-table">
      <tr id="calendar-top-row">
        <td>Sun.<br>1</td>
        <td>Mon.<br>2</td>
        <td>Tue.<br>3</td>
        <td>Wed.<br>4</td>
        <td>Thu.<br>5</td>
        <td>Fri.<br>6</td>
        <td>Sat.<br>7</td>
      </tr>
      <tr id="available-slots">
        <td>
          <div id="availability-block">9:00<br>10:00</div>
          <div id="availability-block">11:00<br>13:00</div>
          <div id="availability-block">14:30<br>14:45</div>
          <div id="availability-block">15:00<br>16:00</div>
          <div id="availability-block">16:00<br>22:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
        <td>
          <div id="availability-block">9:00<br>17:00</div>
        </td>
      </tr>
    </table>
  </div>
</div>
</template>
<style>

.sidemenu {
  background-color: #353A57;
  width: 25%;
  height: 100vh;
  float: left;
  padding: 10px;
  color: white;
}

.calendar {
  background-color: #CDD7DE;
  width: 75%;
  height: 100vh;
  float: right;
  padding: 10px;
}

.service-table {
  overflow-y: auto;
  height: 30vh;
}

.service-table thead th {
  position: sticky;
  top: 0;
}

#service-table {
  border-collapse: collapse;
  width: 100%;
  background-color: #EAF0F4;
  color: black;
}

#service {
  margin-left: 10px;
}

#servicediv {
  margin-top: 5px;
}

th {
  background: white;
}

.datetime-block {
  margin-top: 30px;
  text-align: left;
  font-size: 20px;
}

.datetime-block h3 {
  text-align: center;
}

#datetimepicker {
  margin-left: 10px;
}

.back {
  position: absolute;
    bottom: 10px;
    left: 0%;
    margin-left: 10px;
}

#month-header {
  margin-bottom: 5px;
  font-size: 20px;
}

#month {
  margin-left: 5px;
  margin-right: 5px;
}

#appointments-table {
  width: 100%;
}

tr, td {
  border: 1px solid black;
  text-align: center;
}

/*The rows of the appointment calendar:
*/
#calendar-top-row {
  background-color: white;
  font-size: 20px;
}

#available-slots {
  height: calc(100vh - 150px);
  background-color: #EAF0F4;
}

/*Availability blocks in the appointment calendar:
*/
#availability-block {
  background-color: white;
  width: 95%;
  border-radius: 50px;
  border: 1px solid black;
  margin: 0 auto;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 5px;
  margin-top: 5px;
}

body {
  overflow: hidden; /*This stops the page from having a scroll bar (might be able to remove this later)*/
}

#app {
  margin-top: 0;
}

#test-div {
  text-color: grey;
}

</style>

<script>
</script>