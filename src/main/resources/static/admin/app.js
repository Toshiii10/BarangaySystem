// --- FRONTEND SECURITY GUARD ---
const role = localStorage.getItem('userRole');
if (role !== 'ADMIN') {
    // If they aren't an admin, kick them back to the login screen
    window.location.href = '/login.html';
}
// -------------------------------

// 1. When the page loads, fetch the residents
document.addEventListener("DOMContentLoaded", loadResidents);

// GET: Fetch residents and populate the table
async function loadResidents() {
    const response = await fetch('/api/residents'); 
    const residents = await response.json();
    
    const tableBody = document.getElementById('residentTableBody');
    tableBody.innerHTML = ''; // Clear existing rows

    residents.forEach(resident => {
        const row = `<tr>
            <td>${resident.id}</td>
            <td>${resident.firstName}</td>
            <td>${resident.lastName}</td>
            <td>${resident.zone}</td>
            <td>${resident.civilStatus}</td>
            <td class="text-center">
                <!-- This button links directly to our clearance page -->
                <a href="clearance.html?id=${resident.id}" class="btn btn-sm btn-outline-primary" target="_blank">
                    Generate Clearance
                </a>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

// POST: Send new resident data when the form is submitted
document.getElementById('residentForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevents the page from refreshing

    // Gather data from the input fields
    const newResident = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        zone: document.getElementById('zone').value,
        civilStatus: document.getElementById('civilStatus').value
    };

    // Send the POST request
    const response = await fetch('/api/residents', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newResident)
    });

    if (response.ok) {
        document.getElementById('residentForm').reset(); // Clear form
        loadResidents(); // Refresh the table automatically
    } else {
        alert("Error saving resident!");
    }
});

// Logout Logic
const logoutBtn = document.getElementById('logoutBtn');
if (logoutBtn) {
    logoutBtn.addEventListener('click', function() {
        localStorage.clear(); // Wipe credentials
        window.location.href = '/login.html'; // Send back to front door
    });
}