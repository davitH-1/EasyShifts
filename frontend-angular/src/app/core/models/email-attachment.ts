export interface EmailAttachment {
  id: string; // Unique ID from the database
  fileName: string; // e.g., "invoice.pdf"
  fileSize: number; // Size in bytes (e.g., 102400)
  contentType: string; // MIME type (e.g., "application/pdf")
  url: string; // The download/preview link from your storage (S3/Azure)
}
