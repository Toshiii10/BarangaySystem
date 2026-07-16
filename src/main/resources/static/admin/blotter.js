// --- FRONTEND SECURITY GUARD ---
const role = localStorage.getItem('userRole');
if (role !== 'ADMIN') {
    // If they aren't an admin, kick them back to the login screen
    window.location.href = '/login.html';
}
// -------------------------------

document.addEventListener("DOMContentLoaded", loadBlotters);

// Fetch and display all blotter records
async function loadBlotters() {
    const response = await fetch('/api/blotter');
    const records = await response.json();
    
    const tableBody = document.getElementById('blotterTableBody');
    tableBody.innerHTML = ''; 

    records.forEach(record => {
        // Determine button color based on status
        const statusBadge = record.status === 'Active' ? 'bg-warning text-dark' : 'bg-success';
        
        // Only show the "Mark Settled" button if the case is still Active
        const actionButton = record.status === 'Active' 
            ? `<button onclick="settleCase(${record.id})" class="btn btn-sm btn-outline-success">Mark Settled</button>` 
            : `<span class="text-muted">Resolved</span>`;

        const row = `<tr>
            <td>#${record.id}</td>
            <td>${record.incidentDate}</td>
            <td>${record.incidentType}</td>
            <td>${record.respondentName}</td>
            <td><span class="badge ${statusBadge}">${record.status}</span></td>
            <td class="text-center">${actionButton}</td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

// Submit a new blotter record
document.getElementById('blotterForm').addEventListener('submit', async function(event) {
    event.preventDefault(); 

    const complainantId = document.getElementById('complainantId').value;
    const newRecord = {
        respondentName: document.getElementById('respondentName').value,
        incidentType: document.getElementById('incidentType').value,
        incidentDate: document.getElementById('incidentDate').value,
        narrative: document.getElementById('narrative').value
    };

    const response = await fetch(`/api/blotter/file/${complainantId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newRecord)
    });

    if (response.ok) {
        document.getElementById('blotterForm').reset();
        loadBlotters(); 
        alert("Blotter record filed successfully!");
    } else {
        alert("Error: Make sure the Complainant ID belongs to a valid resident.");
    }
});

// Update case status to Settled via PUT request
async function settleCase(blotterId) {
    if (!confirm("Are you sure you want to mark this case as settled?")) return;

    const response = await fetch(`/api/blotter/update/${blotterId}?status=Settled`, {
        method: 'PUT'
    });

    if (response.ok) {
        loadBlotters(); // Refresh table to show updated status
    } else {
        alert("Error updating case status.");
    }
}

// Logout Logic
const logoutBtn = document.getElementById('logoutBtn');
if (logoutBtn) {
    logoutBtn.addEventListener('click', function() {
        localStorage.clear(); // Wipe credentials
        window.location.href = '/login.html'; // Send back to front door
    });
}