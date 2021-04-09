console.log("team-table.js");

export default class TeamTable extends HTMLElement {

    constructor() {
        super();
        this.innerHTML = `
<table>
    <thead>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Job Title</th>
    </tr>
    </thead>
    <tbody id="teams-tbody">
    <tr>
    <td><a href="/team/17">gurka</a></td>
    </tr>
    </tbody>
</table>
        `;

        this.teamsBody = document.getElementById("teams-tbody");

        this.fetchTeams();
    }

    async fetchTeams() {
        const response = await fetch("http://localhost:9080/api/teams");
        const teams = await response.json();
        console.log("Teams: " + teams);
        for (let team of teams) {
            console.log("Team: " + team.name);
            let tr = document.createElement("tr");
            tr.innerHTML = `<td><a href="/team/${team.pk}">${team.name}</a></td><td>${team.name}</td><td>${team.name}</td>`;
            this.teamsBody.appendChild(tr);
        }
        this.teams = teams;
    }

    connectedCallback() {
        console.log("ConnectedCallback: TeamTable");
    }

    disconnectedCallback() {
        console.log("DisconnectedCallback: TeamTable");
    }

}

// Define the new element
customElements.define("go-team-table", TeamTable);

