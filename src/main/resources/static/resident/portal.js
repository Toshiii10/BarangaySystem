// --- FRONTEND SECURITY GUARD ---
const role = localStorage.getItem('userRole');
const residentId = localStorage.getItem('residentId');

if (role !== 'RESIDENT' || !residentId) {
    // If they aren't a resident (or if their ID is missing), kick them out
    window.location.href = '/login.html';
}
// -------------------------------

document.addEventListener("DOMContentLoaded", loadResidentProfile);

// Fetch only this specific resident's data
async function loadResidentProfile() {
    try {
        const response = await fetch(`/api/residents/${residentId}`);
        if (response.ok) {
            const resident = await response.json();
            
            // Populate the UI
            document.getElementById('welcomeName').textContent = resident.firstName;
            document.getElementById('profileId').textContent = resident.id;
            document.getElementById('profileName').textContent = `${resident.firstName} ${resident.lastName}`;
            document.getElementById('profileZone').textContent = resident.zone;
            document.getElementById('profileStatus').textContent = resident.civilStatus;
        } else {
            alert("Error loading profile data.");
        }
    } catch (error) {
        console.error("Connection error:", error);
    }
}

// Handle filing a blotter as a Resident
document.getElementById('residentBlotterForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const newRecord = {
        respondentName: document.getElementById('respondentName').value,
        incidentType: document.getElementById('incidentType').value,
        incidentDate: document.getElementById('incidentDate').value,
        narrative: document.getElementById('narrative').value
    };

    // We securely pass their stored residentId as the Complainant ID
    const response = await fetch(`/api/blotter/file/${residentId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newRecord)
    });

    if (response.ok) {
        alert("Incident reported successfully. The barangay will review your case.");
        
        // Hide modal and reset form
        const modal = bootstrap.Modal.getInstance(document.getElementById('blotterModal'));
        modal.hide();
        document.getElementById('residentBlotterForm').reset();
    } else {
        alert("Error filing report.");
    }
});

// Logout Logic
document.getElementById('logoutBtn').addEventListener('click', function() {
    localStorage.clear(); // Wipe the credentials from the browser
    window.location.href = '/login.html';
});

// Handle Requesting a Document
document.getElementById('documentRequestForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const requestPayload = {
        documentType: document.getElementById('docType').value,
        purpose: document.getElementById('docPurpose').value
    };

    try {
        const response = await fetch(`/api/documents/request/${residentId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestPayload)
        });

        if (response.ok) {
            const savedRequest = await response.json();
            
            // Save the specific Request ID to local storage so the checkout page knows what to pay for
            localStorage.setItem('pendingDocumentId', savedRequest.id);
            
            // Redirect to the Mock Payment Portal
            window.location.href = '/resident/checkout.html';
        } else {
            // This will display our anti-duplication error message!
            const errorMsg = await response.text();
            alert(errorMsg); 
        }
    } catch (error) {
        console.error("Connection error:", error);
    }
});