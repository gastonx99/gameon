console.log("home.js");

export default class Home extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<h1>Home Page</h1>
    `;
    }
}

customElements.define("go-home", Home);
