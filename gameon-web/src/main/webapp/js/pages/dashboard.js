export default class Dashboard extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<go-bettinggameuserwidget game-status="active"></go-bettinggameuserwidget>
<br/>
<br/>
<go-bettinggameuserwidget game-status="completed"></go-bettinggameuserwidget>
    `;
    }
}

customElements.define("go-dashboard", Dashboard);
