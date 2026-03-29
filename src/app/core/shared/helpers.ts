const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;    
const cpfRegex : RegExp = /^\d{11}$/
const cepRegex : RegExp = /^\d{5}-\d{3}$/

export function validateEmail(email: string): boolean{
    return emailRegex.test(email);
}

export function validateCPF(cpf: string): boolean{
    if(!cpf) return false;
    
    console.log("CPF a validar:", cpf);
    return cpfRegex.test(cpf);
}

export function validateCEP(cep: string): boolean{
    if(!cep) return false;

    console.log("cep a validar:", cep);
    return cepRegex.test(cep);
}
