// --- FRONTEND SECURITY GUARD ---
const role = localStorage.getItem('userRole');
if (role !== 'ADMIN') {
    // If they aren't an admin, kick them back to the login screen
    window.location.href = '/login.html';
}
// -------------------------------

document.addEventListener("DOMContentLoaded", loadStats);

async function loadStats() {
    try {
        const response = await fetch('/api/dashboard/stats');
        if (!response.ok) throw new Error("Failed to fetch statistics");
        
        const stats = await response.json();
        
        // Inject the numbers into the HTML
        document.getElementById('totalResidents').textContent = stats.totalResidents;
        document.getElementById('pendingDocuments').textContent = stats.pendingDocuments;
        document.getElementById('activeBlotters').textContent = stats.activeBlotters;
        
    } catch (error) {
        console.error("Error loading dashboard stats:", error);
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