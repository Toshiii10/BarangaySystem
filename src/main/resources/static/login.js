document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault(); 

    const errorAlert = document.getElementById('errorAlert');
    errorAlert.classList.add('d-none'); 

    // Capture the selected role from the dropdown
    const payload = {
        role: document.getElementById('loginRole').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            const data = await response.json();
            
            localStorage.setItem('userRole', data.role);
            if (data.residentId) {
                localStorage.setItem('residentId', data.residentId);
            }

            // Redirect based on the verified role
            if (data.role === 'ADMIN') {
                window.location.href = '/admin/dashboard.html';
            } else if (data.role === 'RESIDENT') {
                window.location.href = '/resident/portal.html';
            }
        } else {
            // Read the custom error message sent by the backend
            const errorText = await response.text();
            errorAlert.textContent = errorText || "Invalid credentials.";
            errorAlert.classList.remove('d-none');
        }
    } catch (error) {
        console.error("Connection error:", error);
    }
});