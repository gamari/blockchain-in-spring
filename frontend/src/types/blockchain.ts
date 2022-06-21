export type BlockType = {
    previous_hash: string
    nonce: string
    timestamp: Date
    transactions?: [TransactionType]
}

export type TransactionType = {
    sender_address: string
    recipient_address: string
    value: string
}

export type ChainType = {
    chain: [BlockType]
}

export type WalletInformation = {
    private_key: string;
    public_key: string;
    wallet_address: string;
    amount: string;
}