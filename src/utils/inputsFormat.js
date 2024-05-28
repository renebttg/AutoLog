export const formatLicencePlate = (value) => {
  const alphanumericValue = value.replace(/[^a-zA-Z0-9]/g, "");
  const firstPart = alphanumericValue.slice(0, 3).toUpperCase();
  let formattedValue = firstPart;
  if (alphanumericValue.length > 3) {
    formattedValue += "-";
  }
  const secondPart = alphanumericValue.slice(3, 7).toUpperCase();
  formattedValue += secondPart;
  return formattedValue;
};

export const formatChassisNumber = (value) => {
  const alphanumericValue = value.replace(/[^a-zA-Z0-9]/g, "");
  const formattedValue = alphanumericValue.slice(0, 17).toUpperCase();
  return formattedValue;
};

export const formatCNPJ = (value) => {
  const numericValue = value.replace(/\D/g, "");
  const formattedValue = numericValue.replace(
    /^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2}).*/,
    "$1.$2.$3/$4-$5"
  );
  return formattedValue;
};

export const formatPhone = (value) => {
  const numericValue = value.replace(/\D/g, "");
  const formattedValue = numericValue.replace(
    /^(\d{2})(\d{5})(\d{4}).*/,
    "($1) $2-$3"
  );
  return formattedValue;
};

export const validateEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

export const validatePassword = (password) => {
  return password.length >= 6;
};

export const validateStrongPassword = (password) => {
  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  return passwordRegex.test(password);
};

export const validatePhone = (phone) => {
  const phoneRegex = /^\(?\d{2}\)?[\s-]?\d{4,5}[\s-]?\d{4}$/;
  return phoneRegex.test(phone);
};
