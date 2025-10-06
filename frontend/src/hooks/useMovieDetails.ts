import { useCallback, useEffect, useState } from "react";
import apiClient from "../lib/apiClientImplementation";
import type { MovieDetails } from "../lib/types";

const useMovieDetails = (id: number) => {
    const [movieDetails, setMovieDetails] = useState<MovieDetails | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const loadMovieDetails = useCallback( async () => {
        try { 
            setMovieDetails(await apiClient.getMovieDetails(id)); 
        }
        catch (e) { 
            setError((e as Error).message ?? 'Failed to load movie details'); 
        }
        finally{ 
            setLoading(false); 
        }
    }, [id]);

    useEffect(() => { 
        setLoading(true); // Also need to set loading to true!
        loadMovieDetails() 
    }, [loadMovieDetails]);

    return { movieDetails, error, loading, loadMovieDetails }
}

export default useMovieDetails;