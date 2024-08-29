document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('errorModalContainer');
    const errorMessage = container.getAttribute('data-error-message');
    const errorModal = document.getElementById('errorModal');

    if (errorMessage && errorMessage !== 'null' && errorModal) {
        errorModal.style.display = "block";

        const closeButton = errorModal.querySelector('.close');
        if (closeButton) {
            closeButton.addEventListener('click', () => {
                errorModal.style.display = "none";
            });
        }

        // 모달 외부 클릭 시 닫기
        window.addEventListener('click', (event) => {
            if (event.target === errorModal) {
                errorModal.style.display = "none";
            }
        });
    }
});