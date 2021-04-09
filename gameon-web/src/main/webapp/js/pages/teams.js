export default class Teams extends HTMLElement {
    constructor() {
        super();

        const linkElem = document.createElement('link');
        linkElem.setAttribute('rel', 'stylesheet');
        linkElem.setAttribute('href', '/css/main.css');

        const div = document.createElement('div');

        div.innerHTML = `
<h1>Teams</h1>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Country</th>
        <th>Continent</th>
    </tr>
    </thead>
    <tbody id="tbody">
    </tbody>
</table>
    `;

        const shadow = this.attachShadow({mode: 'open'});
        shadow.appendChild(linkElem);
        shadow.appendChild(div);
        this.teamsBody = shadow.getElementById("tbody");
    }

    connectedCallback() {
        this.fetchTeams();
    }

    async fetchTeams() {
        const response = await fetch("http://localhost:9080/api/team");
        const teams = await response.json();
        console.log("Teams: " + teams);
        for (let team of teams) {
            console.log("Team: " + team.name);
            let tr = document.createElement("tr");
            tr.innerHTML = `
<td><a href="/team/${team.pk}">${team.name}</a></td><td>${team.countryName}</td><td>${team.countryContinent}</td>
`;
            this.teamsBody.appendChild(tr);
        }
        this.teams = teams;
    }
}

customElements.define("go-teams", Teams);
