export default class Teams extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<div class="page">
    <h1>Teams</h1>
    <table>
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Job Title</th>
        </tr>
        </thead>
        <tbody id="teams-tbody">
        </tbody>
    </table>
</div>
    `;
        this.teamsBody = document.getElementById("teams-tbody");
        this.fetchTeams();
    }

    async fetchTeams() {
        const response = await fetch("http://localhost:9080/api/team");
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
}

customElements.define("go-teams", Teams);
