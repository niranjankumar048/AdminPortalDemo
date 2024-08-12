$(document).ready(function() {
    loadHomePage();
    $('#home-link').click(function() {
        loadHomePage();
    });

    $('#client-organisations-link').click(function() {
        loadClientOrganisations();
    });

    $('#personnel-link').click(function() {
        loadPersonnel();
    });

    // Function to show popup in case of an error
    function showErrorPopup(message) {
        let displayMessage = message;
        // Check if the message contains "Unique index or primary key violation"
        if (message.includes("Unique index or primary key violation")) {
            displayMessage = "An organisation with this name already exists. Please choose a different name.";
        }
        const errorPopup = $('<div class="error-popup"></div>').text(displayMessage);
        $('body').append(errorPopup);

        // Automatically remove the popup after 3 seconds
        setTimeout(() => {
            errorPopup.fadeOut(500, () => {
                errorPopup.remove();
            });
        }, 3000);
    }
    function loadHomePage() {
        $('#content').html('<h2>Welcome to the Admin Portal</h2>');
        $.get('http://localhost:8080/api/client-organisations', function(data) {
            const enabledCount = data.filter(org => org.enabled).length;
            const content = `
                <p>Enabled Client Organisations: ${enabledCount}</p>
                ${data.length>0 ? '<p>Client list:</p>' : ''}
                ${data.map(org => `
                    <div style="color: ${!org.enabled ? 'red' : 'black'}" >
                        ${org.name} - ${org.enabled ? 'Enabled' : 'Disabled'}
                        ${org.enabled && org.expiringSoon ? '<span style="color: orange">(Expiring Soon)</span>' : ''}
                    </div>
                `).join('')}
            `;
            $('#content').append(content);
        });
    }

    function loadClientOrganisations() {
        $('#content').html(`
            <h1>Client Organisations</h1>
            <button id="add-client-organisation">Add New Client Organisation</button>
            <div id="client-organisation-form"></div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Registration Date</th>
                        <th>Expiry Date</th>
                        <th>Enabled</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="client-organisation-list"></tbody>
            </table>
        `);
        loadClientOrganisationList();

        $('#add-client-organisation').click(function() {
            $('#client-organisation-form').html(`
                <h2>Add Client Organisation</h2>
                <form id="client-organisation-add-form">
                    <label>Name</label>
                    <input type="text" id="name" required>
                    <label>Registration Date</label>
                    <input type="date" id="registrationDate" required>
                    <label>Expiry Date</label>
                    <input type="date" id="expiryDate" required>
                    <label>Enabled</label>
                    <input type="checkbox" id="enabled" checked>
                    <button type="submit">Submit</button>
                </form>
            `);

            $('#client-organisation-add-form').submit(function(event) {
                event.preventDefault();
                const clientOrganisation = {
                    name: $('#name').val(),
                    registrationDate: $('#registrationDate').val(),
                    expiryDate: $('#expiryDate').val(),
                    enabled: $('#enabled').is(':checked')
                };
                $.ajax({
                    url: 'http://localhost:8080/api/client-organisations',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(clientOrganisation),
                    success: function() {
                        loadClientOrganisationList();
                        $('#client-organisation-form').empty();
                    },
                    error: function(xhr, status, error) {
                        // Show error popup with appropriate message
                        let errorMessage = xhr.responseJSON?.message || 'An error occurred while creating the organisation.';
                        showErrorPopup(errorMessage);
                    }
                });
            });
        });
    }

    function loadClientOrganisationList() {
        $.get('http://localhost:8080/api/client-organisations', function(data) {
            const rows = data.map(org => `
                <tr>
                    <td>${org.name}</td>
                    <td>${org.registrationDate}</td>
                    <td>${org.expiryDate}</td>
                    <td>${org.enabled}</td>
                    <td>
                        <button class="edit-client-organisation" data-id="${org.id}">Edit</button>
                        <button class="delete-client-organisation" data-id="${org.id}">Delete</button>
                        <button class="add-personnel" data-id="${org.id}">Add Personnel</button>
                        <button class="view-personnel" data-id="${org.id}">View Personnel</button>
                    </td>
                </tr>
            `).join('');
            $('#client-organisation-list').html(rows);

            $('.edit-client-organisation').click(function() {
                const id = $(this).data('id');
                $.get(`http://localhost:8080/api/client-organisations/${id}`, function(data) {
                    $('#client-organisation-form').html(`
                        <h2>Edit Client Organisation</h2>
                        <form id="client-organisation-edit-form">
                            <label>Name</label>
                            <input type="text" id="name" value="${data.name}" required>
                            <label>Registration Date</label>
                            <input type="date" id="registrationDate" value="${data.registrationDate}" required>
                            <label>Expiry Date</label>
                            <input type="date" id="expiryDate" value="${data.expiryDate}" required>
                            <label>Enabled</label>
                            <input type="checkbox" id="enabled" ${data.enabled ? 'checked' : ''}>
                            <button type="submit">Submit</button>
                        </form>
                    `);

                    $('#client-organisation-edit-form').submit(function(event) {
                        event.preventDefault();
                        const updatedClientOrganisation = {
                            name: $('#name').val(),
                            registrationDate: $('#registrationDate').val(),
                            expiryDate: $('#expiryDate').val(),
                            enabled: $('#enabled').is(':checked')
                        };
                        $.ajax({
                            url: `http://localhost:8080/api/client-organisations/${id}`,
                            type: 'PUT',
                            contentType: 'application/json',
                            data: JSON.stringify(updatedClientOrganisation),
                            success: function() {
                                loadClientOrganisationList();
                                $('#client-organisation-form').empty();
                            }
                        });
                    });
                });
            });

            $('.delete-client-organisation').click(function() {
                const id = $(this).data('id');
                $.ajax({
                    url: `http://localhost:8080/api/client-organisations/${id}`,
                    type: 'DELETE',
                    success: function() {
                        loadClientOrganisationList();
                    }
                });
            });

            $('.add-personnel').click(function() {
                const clientId = $(this).data('id');
                $('#client-organisation-form').html(`
                    <h2>Add Personnel to Client Organisation</h2>
                    <form id="personnel-add-form">
                        <label>First Name</label>
                        <input type="text" id="firstName" required>
                        <label>Last Name</label>
                        <input type="text" id="lastName" required>
                        <label>Username</label>
                        <input type="text" id="username" required>
                        <label>Password</label>
                        <input type="password" id="password" required>
                        <label>E-mail Address</label>
                        <input type="email" id="email" required>
                        <label>Telephone Number</label>
                        <input type="text" id="telephoneNumber" required>
                        <button type="submit">Submit</button>
                    </form>
                `);

                $('#personnel-add-form').submit(function(event) {
                    event.preventDefault();
                    const personnel = {
                        firstName: $('#firstName').val(),
                        lastName: $('#lastName').val(),
                        username: $('#username').val(),
                        password: $('#password').val(),
                        emailAddress: $('#email').val(),
                        telephoneNumber: $('#telephoneNumber').val(),
                        clientOrganisation: {
                            id: clientId
                        }
                    };
                    $.ajax({
                        url: 'http://localhost:8080/api/personnel',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(personnel),
                        success: function() {
                            loadClientOrganisationList();
                            $('#client-organisation-form').empty();
                        }
                    });
                });
            });
            $('.view-personnel').click(function() {
                loadPersonnel();
            });
        });
    }

    function loadPersonnel() {
        $('#content').html(`
            <h1>Personnel</h1>
            <table>
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                        <th>Email Address</th>
                        <th>Telephone Number</th>
                        <th>Client Organisation</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="personnel-list"></tbody>
            </table>
        `);
        loadPersonnelList();
    }

    function loadPersonnelList() {
        $.get('http://localhost:8080/api/personnel', function(data) {
            const rows = data.map(person => `
                <tr>
                    <td>${person.firstName}</td>
                    <td>${person.lastName}</td>
                    <td>${person.username}</td>
                    <td>${person.emailAddress}</td>
                    <td>${person.telephoneNumber}</td>
                    <td>${person.clientOrganisation.name}</td>
                    <td>
                        <button class="edit-personnel" data-id="${person.id}">Edit</button>
                        <button class="delete-personnel" data-id="${person.id}">Delete</button>
                    </td>
                </tr>
            `).join('');
            $('#personnel-list').html(rows);

            $('.edit-personnel').click(function() {
                const id = $(this).data('id');

                console.log(`Fetching personnel with ID: ${id}`); // Debugging

                $.when(
                    $.get(`http://localhost:8080/api/personnel/${id}`),
                    $.get('http://localhost:8080/api/client-organisations')
                ).done(function(personnelResponse, clientsResponse) {
                    const data = personnelResponse[0];
                    const clients = clientsResponse[0];

                    console.log("Personnel data fetched:", data); // Debugging
                    console.log("Client organizations fetched:", clients); // Debugging

                    if (!data || !clients) {
                        console.error("No data received for personnel or client organisations");
                        return;
                    }

                    $('#content').html(`
                                    <h2>Edit Personnel</h2>
                                    <form id="personnel-edit-form">
                                        <label>First Name</label>
                                        <input type="text" id="firstName" value="${data.firstName}" required>
                                        <label>Last Name</label>
                                        <input type="text" id="lastName" value="${data.lastName}" required>
                                        <label>Username</label>
                                        <input type="text" id="username" value="${data.username}" required>
                                        <label>Password</label>
                                        <input type="password" id="password">
                                        <label>E-mail Address</label>
                                        <input type="email" id="email" value="${data.emailAddress}" required>
                                        <label>Telephone Number</label>
                                        <input type="text" id="telephoneNumber" value="${data.telephoneNumber}" required>
                                        <label>Client Organisation</label>
                                        <select id="clientId" required></select>
                                        <button type="submit">Submit</button>
                                    </form>
                                `);

                    clients.forEach(client => {
                        $('#clientId').append(`
                                        <option value="${client.id}" ${client.id === data.clientOrganisation.id ? 'selected' : ''}>${client.name}</option>
                                    `);
                    });

                    $('#personnel-edit-form').submit(function(event) {
                        event.preventDefault();
                        const updatedPersonnel = {
                            firstName: $('#firstName').val(),
                            lastName: $('#lastName').val(),
                            username: $('#username').val(),
                            emailAddress: $('#email').val(),
                            telephoneNumber: $('#telephoneNumber').val(),
                            clientOrganisation: {
                                id: $('#clientId').val()
                            }
                        };
                        if ($('#password').val()) {
                            updatedPersonnel.password = $('#password').val();
                        }

                        console.log("Updating personnel with data:", updatedPersonnel); // Debugging

                        $.ajax({
                            url: `http://localhost:8080/api/personnel/${id}`,
                            type: 'PUT',
                            contentType: 'application/json',
                            data: JSON.stringify(updatedPersonnel),
                            success: function() {
                                loadPersonnel();
                            }
                        });
                    });
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    console.error("AJAX call failed:", textStatus, errorThrown); // Debugging
                });
            });

            $('.delete-personnel').click(function() {
                const id = $(this).data('id');
                $.ajax({
                    url: `http://localhost:8080/api/personnel/${id}`,
                    type: 'DELETE',
                    success: function() {
                        loadPersonnelList();
                    }
                });
            });
        });
    }
});