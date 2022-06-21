import { WalletInformation } from "../types/blockchain";

export function fetch_chain(fetch_url: string): Promise<WalletInformation> {
    return fetch(fetch_url, {
        method: "POST",
        mode: "cors",
        headers: {
            "Content-Type": "application/json",
        },
    }).then((res) => res.json()).then(data => {
        const wallet_information: WalletInformation = {
            private_key: data["privateKey"],
            public_key: data["publicKey"],
            wallet_address: data["blockchainAddress"],
            amount: data["amount"],
        }

        return wallet_information;
    });

}

