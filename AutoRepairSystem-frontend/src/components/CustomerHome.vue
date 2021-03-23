<template>
  <div id="evenregistration">
    <h2>People</h2>
    <table>
        <tr v-for="person in persons" >
            <td>{{ person.name }}</td>
            <td>
              <ul>
                <li v-for="event in person.events">
                  {{event.name}}
                </li>
              </ul>
            </td>
        </tr>
      <tr>
          <td>
              <input type="text" v-model="newPerson" placeholder="Person Name">
          </td>
          <td>
              <button v-bind:disabled="!newPerson" @click="createPerson(newPerson)" >Create</button>
          </td>
      </tr>
    </table>
    <p>
      <span v-if="errorPerson" style="color:red">Error: {{errorPerson}}</span>
    </p>
  </div>
</template>
<style>
#eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>

<script>
function PersonDto (name) {
  this.name = name
  this.events = []
}

function EventDto (name, date, start, end) {
  this.name = name
  this.eventDate = date
  this.startTime = start
  this.endTime = end
}

export default {
  created: function () {
    // Test data
    const p1 = new PersonDto('John')
    const p2 = new PersonDto('Jill')
    // Sample initial content
    this.persons = [p1, p2]
  },

  name: 'eventregistration',
  data () {
    return {
      persons: [],
      newPerson: '',
      errorPerson: '',
      response: []
    }
  },

  methods: {
    createPerson: function (personName) {
      // Create a new person and add it to the list of people
      var p = new PersonDto(personName)
      this.persons.push(p)
      // Reset the name field for new people
      this.newPerson = ''
    }
  
}
}

</script>