package se.infomaker.actapublica.api

data class Authorization(val grant_type: String ="client_credentials", val client_id: String, val client_secret: String, val scope: String ="document.view,document.list,agent.detail,agent.list,document.download")