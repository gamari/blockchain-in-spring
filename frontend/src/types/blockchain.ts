export type BlockType = {
    previous_hash: string
    nonce: string
    timestamp: Date
    transactions?: [TransactionType]
}

export type TransactionType = {
    recipientAddress: string
    senderAddress: string
    value: string
}

export type ChainType = {
    blocks: [BlockType]
}

export type WalletInformation = {
    private_key: string;
    public_key: string;
    wallet_address: string;
    amount: string;
}
