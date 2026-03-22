export class CurrencyFormatter{

    applyCurrencyMaskOnString (value: string){
    
        if(!value) return "";

        let digits = value.replace(/\D/g, '');

        const numberValue = Number(digits) / 100;

        return this.applyCurrencyMaskOnNumber(numberValue);
    }

    applyCurrencyMaskOnNumber(value:number){

        return new Intl.NumberFormat('pt-BR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(value);

    }
    
    removeCurrencyMaskFromString(value: string){
    
        if (!value) return 0;
        
        const rawStringValue = value
            .replace(/\./g, '') // remove pontos de milhar
            .replace(',', '.'); // troca vírgula decimal por ponto
    
        return Number(rawStringValue);
    }
}
