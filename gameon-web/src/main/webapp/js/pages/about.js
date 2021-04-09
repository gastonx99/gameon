console.log("about.js");

export default class AboutUs extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
<h1>About Us</h1>
    `;
    }
}

customElements.define("go-about", AboutUs);

