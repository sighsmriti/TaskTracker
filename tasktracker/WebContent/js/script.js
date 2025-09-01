document.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('.task-checkbox');

    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            const row = this.closest('tr');
            const taskId = this.dataset.id;
            const completed = this.checked;

            if (completed) {
                row.classList.add('completed-task');
            } else {
                row.classList.remove('completed-task');
            }

            const formData = new URLSearchParams();
            formData.append('id', taskId);
            formData.append('completed', completed);

            fetch('update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData
            }).then(response => {
                if (!response.ok) {
                    console.error('Failed to update task status.');
                }
            }).catch(error => {
                console.error('Error:', error);
            });
        });
    });
});