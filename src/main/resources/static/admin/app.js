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
    
    // 1. If a DataTable already exists, destroy it before updating the data
    if ($.fn.DataTable.isDataTable('#residentTable')) {
        $('#residentTable').DataTable().destroy();
    }

    const tableBody = document.getElementById('residentTableBody');
    tableBody.innerHTML = ''; // Clear existing rows

    // 2. Populate the table with database records
    residents.forEach(resident => {
        const row = `<tr>
            <td>${resident.id}</td>
            <td>${resident.firstName}</td>
            <td>${resident.lastName}</td>
            <td>${resident.zone}</td>
            <td>${resident.civilStatus}</td>
            <td class="text-center">
                <div class="btn-group" role="group">
                    <a href="clearance.html?id=${resident.id}" class="btn btn-sm btn-outline-primary" target="_blank" title="Clearance">
                        📄
                    </a>
                    <button class="btn btn-sm btn-outline-warning" onclick="openEditModal(${resident.id}, '${resident.firstName}', '${resident.lastName}', '${resident.zone}', '${resident.civilStatus}')" title="Edit">
                        ✏️
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteResident(${resident.id})" title="Delete">
                        🗑️
                    </button>
                </div>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });

    // 3. Initialize the DataTable with Bootstrap styling and custom search text
    $('#residentTable').DataTable({
        pageLength: 10, // Show 10 rows per page
        language: {
            search: "_INPUT_",
            searchPlaceholder: "Search residents..."
        }
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
        alert("Resident added successfully!");
        document.getElementById('residentForm').reset(); // Clear form
        loadResidents(); // Refresh the table automatically
    } else {
        // --- NEW: DUPLICATE CATCHER ---
        // If the backend throws a 400 Bad Request, show the Admin exactly why!
        const errorMessage = await response.text();
        alert(errorMessage); 
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

// --- CRUD OPERATIONS: EDIT & DELETE ---

// 1. Open the Edit Modal and inject the existing data
function openEditModal(id, firstName, lastName, zone, civilStatus) {
    document.getElementById('editResidentId').value = id;
    document.getElementById('editFirstName').value = firstName;
    document.getElementById('editLastName').value = lastName;
    document.getElementById('editZone').value = zone;
    document.getElementById('editCivilStatus').value = civilStatus;
    
    const editModal = new bootstrap.Modal(document.getElementById('editResidentModal'));
    editModal.show();
}

// 2. Submit the Updated Data
document.getElementById('editResidentForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    const id = document.getElementById('editResidentId').value;
    
    const updatedData = {
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        zone: document.getElementById('editZone').value,
        civilStatus: document.getElementById('editCivilStatus').value
    };

    const response = await fetch(`/api/residents/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedData)
    });

    if (response.ok) {
        alert("Resident updated successfully!");
        bootstrap.Modal.getInstance(document.getElementById('editResidentModal')).hide();
        loadResidents(); // Reload DataTables
    } else {
        alert("Failed to update resident.");
    }
});

// 3. Delete a Resident
async function deleteResident(id) {
    if (confirm("Are you sure you want to delete this resident? This action cannot be undone.")) {
        const response = await fetch(`/api/residents/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert("Resident deleted successfully.");
            loadResidents(); // Reload DataTables
        } else {
            alert("Error deleting resident.");
        }
    }
}
